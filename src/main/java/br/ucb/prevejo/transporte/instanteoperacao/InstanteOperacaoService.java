package br.ucb.prevejo.transporte.instanteoperacao;

import br.ucb.prevejo.transporte.percurso.EnumSentido;
import br.ucb.prevejo.transporte.percurso.PercursoDTO;
import br.ucb.prevejo.shared.util.DateAndTime;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InstanteOperacaoService {

    private static final InstanteOperacaoService instance = new InstanteOperacaoService();

    private InstanteOperacaoService() {
    }

    private InstanteOperacaoRepository repository = new InstanteOperacaoRepository();

    public Collection<InstanteOperacao> obterByPercurso(PercursoDTO percurso) {
        List<Integer> diasSemana = Arrays.asList(DateAndTime.now().getDayOfWeek().ordinal() + 1);
        //diasSemana = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        if (percurso.getSentido() == EnumSentido.CIRCULAR) {
            return repository.findAllByLinha(percurso.getLinha().getNumero(), diasSemana)
                    .stream().map(inst -> {
                        inst.setSentido(EnumSentido.CIRCULAR);
                        return inst;
                    }).collect(Collectors.toList());
        }

        return repository.findAllByLinhaAndSentido(percurso.getLinha().getNumero(), percurso.getSentido(), diasSemana);
    }

    public static InstanteOperacaoService instance() {
        return instance;
    }

}
