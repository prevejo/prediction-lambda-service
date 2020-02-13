package br.ucb.prevejo.core.interfaces;

import br.ucb.prevejo.core.DynamoDB;
import br.ucb.prevejo.transporte.instanteoperacao.InstanteOperacaoStore;

import java.sql.Connection;
import java.sql.SQLException;

public interface Resources {

    Connection dbConnectionResource() throws SQLException;
    DynamoDB dynamoDBResource();
    InstanteOperacaoStore instanteOperacaoStore();
    void closeResources();

}
