package br.ucb.prevejo.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class Request {

    private String numero;
    private String sentido;
    private String parada;
    private Collection<VeiculoInstante> veiculos;

}
