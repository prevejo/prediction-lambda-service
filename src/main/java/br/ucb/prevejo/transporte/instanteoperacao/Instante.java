package br.ucb.prevejo.transporte.instanteoperacao;

import br.ucb.prevejo.shared.interfaces.LocatedEntity;
import br.ucb.prevejo.shared.model.Velocidade;
import br.ucb.prevejo.shared.util.DateAndTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Instante implements Comparable<Instante>, LocatedEntity {

    @EqualsAndHashCode.Include
    private LocalDateTime data;
    private Point localizacao;
    private BigDecimal direcao;
    private Velocidade velocidade;

    public Instante(LocalDateTime data, Point localizacao) {
        this.data = data;
        this.localizacao = localizacao;
    }

    public Instante(LocalDateTime data, Point localizacao, BigDecimal direcao, Velocidade velocidade) {
        this.data = data;
        this.localizacao = localizacao;
        this.direcao = direcao;
        this.velocidade = velocidade;
    }

    public boolean isBehind(Duration maxDuration) {
        return DateAndTime.isBehind(getData(), maxDuration);
    }

    @JsonIgnore
    @Override
    public Point getLocation() {
        return getLocalizacao();
    }

    @Override
    public int compareTo(Instante instante) {
        return getData().compareTo(instante.getData());
    }
}
