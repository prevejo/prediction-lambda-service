package br.ucb.prevejo.estimativa;

import br.ucb.prevejo.estimativa.model.*;
import br.ucb.prevejo.transporte.instanteoperacao.InstanteOperacao;
import br.ucb.prevejo.transporte.instanteoperacao.InstanteOperacaoService;
import br.ucb.prevejo.transporte.parada.Parada;
import br.ucb.prevejo.transporte.percurso.Percurso;
import br.ucb.prevejo.shared.interfaces.LocatedEntity;

import java.util.Collection;

public class EstimativaService {

    private static final EstimativaService instance = new EstimativaService();

    private EstimativaService() {
    }

    public EstimativaPercurso estimar(Percurso percurso, Parada embarque, Collection<? extends LocatedEntity> veiculos) {
        Collection<InstanteOperacao> operacoes = InstanteOperacaoService.instance().obterByPercurso(percurso.toDTO());

        HistoricoOperacao hp = HistoricoOperacao.build(operacoes);

        return hp.calcularEstimativa(percurso, embarque, veiculos);
    }

    public static EstimativaService instance() {
        return instance;
    }

}
