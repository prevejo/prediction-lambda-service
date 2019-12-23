package br.ucb.prevejo.transporte.parada;

import br.ucb.prevejo.core.JdbcConnectionFactory;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ParadaRepository {

    private static final String SQL_BY_COD = "SELECT " +
                "p.id," +
                "p.cod," +
                "ST_AsBinary(p.geo) as geo " +
            "FROM transporte.tb_parada p " +
            "WHERE p.cod = ?";

    private static final WKBReader GEOMETRY_READER = new WKBReader(new GeometryFactory(new PrecisionModel((int)Math.pow(10, 5)), 4326));

    public Optional<Parada> findByCod(String codParada) {
        return JdbcConnectionFactory.requestConnection(conn -> conn.queryEntity(
                SQL_BY_COD, ps -> ps.setString(1, codParada), rs -> parse(rs)));
    }

    private Parada parse(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String cod = rs.getString("cod");
        byte[] geo = rs.getBytes("geo");

        try {
            return new Parada(
                    id,
                    cod,
                    (Point) GEOMETRY_READER.read(geo)
            );
        } catch(ParseException e) {
            throw new SQLException(e);
        }
    }

}
