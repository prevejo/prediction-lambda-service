package br.ucb.prevejo.transporte.instanteoperacao;

import br.ucb.prevejo.core.DynamoDB;
import br.ucb.prevejo.core.DynamoDBRequest;
import br.ucb.prevejo.core.interfaces.ItemEntityParser;
import br.ucb.prevejo.shared.util.DateAndTime;
import br.ucb.prevejo.transporte.percurso.PercursoDTO;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InstanteOperacaoDynamoStore implements InstanteOperacaoStore {

    private ItemEntityParser<List<InstanteOperacao>> parser = new InstanteOperacaoItemParser();

    @Override
    public Collection<InstanteOperacao> obterByPercurso(PercursoDTO percurso) {
        int diaSemada = DateAndTime.now().getDayOfWeek().ordinal();

        DynamoDBRequest request = DynamoDB.requestTable("tb_localizacao_veiculo");

        Map<String, Object> maps = new HashMap<String, Object>() {{
            put("num_linha", percurso.getLinha().getNumero());
            put("ds_sentido", percurso.getSentido().toString());
        }};

        QuerySpec qs = new QuerySpec()
                .withHashKey("percurso", percurso.getLinha().getNumero() + "_" + percurso.getSentido().toString());

        Collection<List<InstanteOperacao>> instantes = request.query(qs, parser);

        return instantes.stream().flatMap(i -> i.stream())
                .filter(i -> i.getInstante().getData().getDayOfWeek().ordinal() == diaSemada)
                .collect(Collectors.toList());
    }

}
