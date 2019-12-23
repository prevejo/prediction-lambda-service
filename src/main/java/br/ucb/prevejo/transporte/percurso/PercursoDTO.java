package br.ucb.prevejo.transporte.percurso;

public interface PercursoDTO {

    Integer getId();
    EnumSentido getSentido();
    Linha getLinha();
    String getOrigem();
    String getDestino();

}
