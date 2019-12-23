package br.ucb.prevejo.transporte.parada;

import br.ucb.prevejo.shared.interfaces.LocatedEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Parada implements LocatedEntity {

    @EqualsAndHashCode.Include
    private Integer id;

    private String cod;

    private Point geo;

    @JsonIgnore
    @Override
    public Point getLocation() {
        return getGeo();
    }
}
