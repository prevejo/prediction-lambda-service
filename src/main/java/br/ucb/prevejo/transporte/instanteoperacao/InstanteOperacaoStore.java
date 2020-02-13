package br.ucb.prevejo.transporte.instanteoperacao;

import br.ucb.prevejo.transporte.percurso.PercursoDTO;

import java.util.Collection;

public interface InstanteOperacaoStore {

    Collection<InstanteOperacao> obterByPercurso(PercursoDTO percurso);

}
