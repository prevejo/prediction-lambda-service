package br.ucb.prevejo.shared.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Velocidade {

    private EnumVelocidade unidade;
    private BigDecimal valor;

    public static Velocidade metrosPorSegundo(BigDecimal valor) {
        return new Velocidade(EnumVelocidade.METROS_POR_SEG, valor);
    }

    public static Velocidade kilometrosPorHora(BigDecimal valor) {
        return new Velocidade(EnumVelocidade.KM_POR_HORA, valor);
    }

    public static Velocidade build(String unitVelocidade, BigDecimal velocidade) {
        return new Velocidade(EnumVelocidade.valueByAbr(unitVelocidade), velocidade);
    }
}
