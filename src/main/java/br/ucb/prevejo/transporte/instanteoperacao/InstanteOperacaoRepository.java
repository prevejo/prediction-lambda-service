package br.ucb.prevejo.transporte.instanteoperacao;

import br.ucb.prevejo.core.JdbcConnectionFactory;
import br.ucb.prevejo.transporte.percurso.EnumSentido;
import br.ucb.prevejo.shared.model.Velocidade;
import br.ucb.prevejo.shared.util.Geo;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class InstanteOperacaoRepository {

    private static final WKBReader GEOMETRY_READER = new WKBReader(new GeometryFactory(new PrecisionModel((int)Math.pow(10, 5)), 4326));

    private static final String TABLE_NAME = "transporte.tb_localizacao_veiculo";

    private static final String SQL_BY_LINHA = "SELECT num_veiculo as numero, " +
            "num_linha as linha, " +
            "ds_operadora as operadora, " +
            "dt_localizacao as data, " +
            "ds_sentido as sentido," +
            "num_direcao as direcao," +
            "num_velocidade  as velocidade," +
            "ds_velocidade as unit_velocidade," +
            "ST_AsBinary(geo) as localizacao " +
            "FROM "+TABLE_NAME+" where num_linha = ? and " +
            "extract(isodow from dt_localizacao) in (:diasSemana)";

    private static final String SQL_BY_LINHA_AND_SENTIDO = "SELECT num_veiculo as numero, " +
            "num_linha as linha, " +
            "ds_operadora as operadora, " +
            "dt_localizacao as data, " +
            "ds_sentido as sentido," +
            "num_direcao as direcao," +
            "num_velocidade  as velocidade," +
            "ds_velocidade as unit_velocidade," +
            "ST_AsBinary(geo) as localizacao " +
            "FROM "+TABLE_NAME+" where num_linha = ? and ds_sentido = ? and " +
            "extract(isodow from dt_localizacao) in (:diasSemana)";



    public List<InstanteOperacao> findAllByLinha(String linha, List<Integer> diasSemana) {
        String sql = SQL_BY_LINHA.replace(":diasSemana", diasSemana.stream().map(ds -> "?").collect(Collectors.joining(",")));

        return JdbcConnectionFactory.requestConnection(conn -> conn.queryList(sql, (ps) -> {
            ps.setString(1, linha);

            int index = 2;
            for (Integer dia : diasSemana) {
                ps.setInt(index++, dia);
            }
        }, (rs) -> parse(rs)));
    }

    public List<InstanteOperacao> findAllByLinhaAndSentido(String linha, EnumSentido sentido, List<Integer> diasSemana) {
        String sql = SQL_BY_LINHA_AND_SENTIDO.replace(":diasSemana", diasSemana.stream().map(ds -> "?").collect(Collectors.joining(",")));

        return JdbcConnectionFactory.requestConnection(conn -> conn.queryList(sql, (ps) -> {
            ps.setString(1, linha);
            ps.setString(2, sentido.toString());

            int index = 3;
            for (Integer dia : diasSemana) {
                ps.setInt(index++, dia);
            }
        }, (rs) -> parse(rs)));
    }


    private static InstanteOperacao parse(ResultSet rs) throws SQLException {
        try {
            String numero = rs.getString("numero");
            String linha = rs.getString("linha");
            String sentido = rs.getString("sentido");
            String operadora = rs.getString("operadora");
            Timestamp data = rs.getTimestamp("data");
            byte[] localizacao = rs.getBytes("localizacao");

            BigDecimal velocidade = rs.getBigDecimal("velocidade");
            String unitVelocidade = rs.getString("unit_velocidade");
            BigDecimal direcao = rs.getBigDecimal("direcao");
            Point point = null;
            try {
                point = (Point) GEOMETRY_READER.read(localizacao);
            } catch(ParseException e) {
                if (!(e.getMessage() != null && e.getMessage().startsWith("Unknown WKB type"))) {
                    throw e;
                }
                point = Geo.makePoint(-15, -45);
            }

            return new InstanteOperacao(
                    new Veiculo(
                            numero,
                            EnumOperadora.valueOf(operadora)
                    ),
                    linha,
                    sentido != null ? EnumSentido.valueOf(sentido) : null,
                    new Instante(
                            data.toLocalDateTime(),
                            point, direcao, (velocidade != null ? Velocidade.build(unitVelocidade, velocidade) : null)
                    )
            );
        } catch (ParseException e) {
            throw new SQLException(e);
        }
    }

}
