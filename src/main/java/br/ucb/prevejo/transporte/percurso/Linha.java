package br.ucb.prevejo.transporte.percurso;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Linha {

    @EqualsAndHashCode.Include
    private Integer id;
    private String numero;
    private String descricao;
    private BigDecimal tarifa;

}
