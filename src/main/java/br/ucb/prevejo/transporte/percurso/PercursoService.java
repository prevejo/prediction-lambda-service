package br.ucb.prevejo.transporte.percurso;

import java.util.Optional;

public class PercursoService {

    private static final PercursoService instance = new PercursoService();

    private PercursoService() {
    }

    private PercursoRepository repository = new PercursoRepository();

    public Optional<Percurso> obterPercursoFetchLinha(String numLinha, EnumSentido sentido) {
        return repository.findByNumeroAndSentido(numLinha, sentido);
    }

    public static PercursoService instance() {
        return instance;
    }

}
