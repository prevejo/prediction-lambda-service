package br.ucb.prevejo.transporte.instanteoperacao;

import br.ucb.prevejo.core.JdbcConnectionFactory;
import br.ucb.prevejo.core.interfaces.ResultSetEntityParser;
import br.ucb.prevejo.shared.util.DateAndTime;
import br.ucb.prevejo.transporte.percurso.EnumSentido;
import br.ucb.prevejo.transporte.percurso.PercursoDTO;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InstanteOperacaoDBStore implements InstanteOperacaoStore {

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


    private ResultSetEntityParser<InstanteOperacao> parser = new InstanteOperacaoResultSetParser();

    @Override
    public Collection<InstanteOperacao> obterByPercurso(PercursoDTO percurso) {
        List<Integer> diasSemana = Arrays.asList(DateAndTime.now().getDayOfWeek().ordinal() + 1);
        //diasSemana = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        if (percurso.getSentido() == EnumSentido.CIRCULAR) {
            return findAllByLinha(percurso.getLinha().getNumero(), diasSemana)
                    .stream().map(inst -> {
                        inst.setSentido(EnumSentido.CIRCULAR);
                        return inst;
                    }).collect(Collectors.toList());
        }

        return findAllByLinhaAndSentido(percurso.getLinha().getNumero(), percurso.getSentido(), diasSemana);
    }

    public List<InstanteOperacao> findAllByLinha(String linha, List<Integer> diasSemana) {
        String sql = SQL_BY_LINHA.replace(":diasSemana", diasSemana.stream().map(ds -> "?").collect(Collectors.joining(",")));

        return JdbcConnectionFactory.requestConnection(conn -> conn.queryList(sql, (ps) -> {
            ps.setString(1, linha);

            int index = 2;
            for (Integer dia : diasSemana) {
                ps.setInt(index++, dia);
            }
        }, parser));
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
        }, parser));
    }

}
