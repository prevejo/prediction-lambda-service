package br.ucb.prevejo.transporte.percurso;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.locationtech.jts.geom.LineString;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Percurso {

    @EqualsAndHashCode.Include
    private Integer id;

    private Linha linha;

    private EnumSentido sentido;

    private String origem;

    private String destino;

    private LineString geo;

    public EnumSentido getSentido() {
        return sentido;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public LineString getGeo() {
        return geo;
    }

    @JsonIgnore
    public PercursoDTO toDTO() {
        return new PercursoDTOImpl(getId(), getSentido(), getLinha(), getOrigem(), getDestino());
    }

}
