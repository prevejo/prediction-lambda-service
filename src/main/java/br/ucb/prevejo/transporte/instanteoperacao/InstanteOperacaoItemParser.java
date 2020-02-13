package br.ucb.prevejo.transporte.instanteoperacao;

import br.ucb.prevejo.core.interfaces.ItemEntityParser;
import br.ucb.prevejo.shared.model.Velocidade;
import br.ucb.prevejo.shared.util.DateAndTime;
import br.ucb.prevejo.shared.util.Geo;
import br.ucb.prevejo.shared.util.IOUtil;
import br.ucb.prevejo.transporte.percurso.EnumSentido;
import com.amazonaws.services.dynamodbv2.document.Item;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InstanteOperacaoItemParser implements ItemEntityParser<List<InstanteOperacao>> {

    private DateTimeFormatter DT_LOCALIZACAO_FORMAT = DateAndTime.buildFormater("yyyy-MM-dd HH:mm:ss");
    private JSONParser JSON_PARSER = new JSONParser();

    @Override
    public List<InstanteOperacao> parse(Item item) {
        String linha = item.getString("num_linha");
        String sentido = item.getString("ds_sentido");
        Stream<JSONObject> veiculos;

        try {
            veiculos = ((JSONArray) JSON_PARSER.parse(
                    new String(IOUtil.unzip(item.getBinary("veiculos")))
            )).stream();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return veiculos.map(obj -> {
            String numero = (String) obj.get("num_veiculo");
            String operadora = (String) obj.get("ds_operadora");

            String velocidade = (String) obj.get("num_velocidade");
            String unidadeVelocidade = (String) obj.get("ds_velocidade");

            String direcao = (String) obj.get("num_direcao");

            String dtLocalizacao = (String) obj.get("dt_localizacao");

            double lon = (Double) obj.get("num_longitude");
            double lat = (Double) obj.get("num_latitude");

            return new InstanteOperacao(
                    new Veiculo(
                            numero,
                            EnumOperadora.valueOf(operadora)
                    ),
                    linha,
                    sentido != null ? EnumSentido.valueOf(sentido) : null,
                    new Instante(
                            LocalDateTime.from(DT_LOCALIZACAO_FORMAT.parse(dtLocalizacao)),
                            Geo.makePointXY(lon, lat),
                            new BigDecimal(direcao),
                            (velocidade != null ? Velocidade.build(unidadeVelocidade, new BigDecimal(velocidade)) : null)
                    )
            );
        }).collect(Collectors.toList());
    }

}
