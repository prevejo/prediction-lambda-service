package br.ucb.prevejo.request;

import br.ucb.prevejo.transporte.parada.Parada;
import br.ucb.prevejo.transporte.percurso.Percurso;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class Request {

    private Percurso percurso;
    private Parada parada;
    private Collection<VeiculoInstante> veiculos;

}
