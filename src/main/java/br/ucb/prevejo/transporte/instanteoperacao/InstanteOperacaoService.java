package br.ucb.prevejo.transporte.instanteoperacao;

import br.ucb.prevejo.core.App;
import br.ucb.prevejo.transporte.percurso.PercursoDTO;

import java.util.*;

public class InstanteOperacaoService {

    private static InstanteOperacaoService instance = null;

    private InstanteOperacaoService() {}

    public Collection<InstanteOperacao> obterByPercurso(PercursoDTO percurso) {
        return App.getResources().instanteOperacaoStore().obterByPercurso(percurso);
    }

    public static InstanteOperacaoService instance() {
        if (instance == null) {
            instance = new InstanteOperacaoService();
        }

        return instance;
    }

}
