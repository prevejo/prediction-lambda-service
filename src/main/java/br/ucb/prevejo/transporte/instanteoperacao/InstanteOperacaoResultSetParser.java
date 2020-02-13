package br.ucb.prevejo.transporte.instanteoperacao;

import br.ucb.prevejo.core.interfaces.ResultSetEntityParser;
import br.ucb.prevejo.shared.model.Velocidade;
import br.ucb.prevejo.shared.util.Geo;
import br.ucb.prevejo.transporte.percurso.EnumSentido;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class InstanteOperacaoResultSetParser implements ResultSetEntityParser<InstanteOperacao> {

    private static final WKBReader GEOMETRY_READER = new WKBReader(new GeometryFactory(new PrecisionModel((int)Math.pow(10, 5)), 4326));

    @Override
    public InstanteOperacao parse(ResultSet rs) throws SQLException {
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
