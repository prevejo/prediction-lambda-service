package br.ucb.prevejo.transporte.parada;

import java.util.Optional;

public class ParadaService {

    private static final ParadaService instance = new ParadaService();

    private ParadaService() {
    }

    private ParadaRepository repository = new ParadaRepository();

    public Optional<Parada> obterPorCodigo(String codigo) {
        return repository.findByCod(codigo);
    }

    public static ParadaService instance() {
        return instance;
    }

}
