package br.ucb.prevejo.estimativa.model;

import br.ucb.prevejo.transporte.percurso.PercursoDTO;
import br.ucb.prevejo.shared.interfaces.LocatedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class EstimativaPercurso {

    private PercursoDTO percurso;
    private LocatedEntity endPoint;
    private Collection<EstimativaChegada> chegadas;

}
