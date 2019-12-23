package br.ucb.prevejo.request;

import br.ucb.prevejo.shared.interfaces.LocatedEntity;
import br.ucb.prevejo.transporte.instanteoperacao.Instante;
import br.ucb.prevejo.transporte.instanteoperacao.InstanteOperacao;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class VeiculoInstante implements LocatedEntity {

    private InstanteOperacao veiculo;
    private List<Instante> historico;

    @Override
    public Point getLocation() {
        return veiculo.getLocation();
    }

    @Override
    public List<Point> getRecordPath() {
        return historico.stream().map(i -> i.getLocalizacao()).collect(Collectors.toList());
    }
}
