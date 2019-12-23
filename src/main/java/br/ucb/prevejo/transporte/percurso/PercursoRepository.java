package br.ucb.prevejo.transporte.percurso;

import br.ucb.prevejo.core.JdbcConnectionFactory;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PercursoRepository {

    private static final String SQL_BY_NUMERO_AND_SENTIDO = "SELECT " +
                "p.id as id_percurso," +
                "p.sentido," +
                "p.origem," +
                "p.destino," +
                "ST_AsBinary(p.geo) as geo," +
                "l.id," +
                "l.numero," +
                "l.descricao," +
                "l.tarifa " +
            "FROM transporte.tb_percurso p " +
                "JOIN transporte.tb_linha l ON l.id = p.id_linha " +
            "WHERE l.numero = ? AND p.sentido = ?";

    private static final WKBReader GEOMETRY_READER = new WKBReader(new GeometryFactory(new PrecisionModel((int)Math.pow(10, 5)), 4326));

    public Optional<Percurso> findByNumeroAndSentido(String numero, EnumSentido sentido) {
        return JdbcConnectionFactory.requestConnection(conn -> conn.queryEntity(SQL_BY_NUMERO_AND_SENTIDO, ps -> {
            ps.setString(1, numero);
            ps.setString(2, sentido.toString());
        }, rs -> parse(rs)));
    }

    private Percurso parse(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_percurso");
        String sentido = rs.getString("sentido");
        String origem = rs.getString("origem");
        String destino = rs.getString("destino");
        byte[] geo = rs.getBytes("geo");

        try {
            return new Percurso(
                    id,
                    parseLinha(rs),
                    EnumSentido.CIRCULAR.valueOf(sentido),
                    origem,
                    destino,
                    (LineString) GEOMETRY_READER.read(geo)
            );
        } catch(ParseException e) {
            throw new SQLException((e));
        }
    }

    private Linha parseLinha(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String numero = rs.getString("numero");
        String descricao = rs.getString("descricao");
        BigDecimal tarifa = rs.getBigDecimal("tarifa");

        return new Linha(id, numero, descricao, tarifa);
    }

}
