package br.ucb.prevejo.core;

import br.ucb.prevejo.core.interfaces.ConnectionFactory;
import br.ucb.prevejo.core.interfaces.Resources;
import br.ucb.prevejo.transporte.instanteoperacao.InstanteOperacaoStore;

import java.sql.Connection;
import java.sql.SQLException;

public class SingletonResources implements Resources {

    private ConnectionFactory dbConnectionFactory;
    private Connection dbConn;
    private DynamoDB dynamoDB;
    private InstanteOperacaoStore store;

    public SingletonResources(ConnectionFactory dbConnectionFactory, DynamoDB dynamoDB, InstanteOperacaoStore store) {
        this.dbConnectionFactory = dbConnectionFactory;
        this.dynamoDB = dynamoDB;
        this.store = store;
    }

    public Connection dbConnectionResource() throws SQLException {
        if (dbConn == null ||  dbConn.isClosed()) {
            dbConn = dbConnectionFactory.createConnection();
        }

        return dbConn;
    }

    public DynamoDB dynamoDBResource() {
        return this.dynamoDB;
    }

    public InstanteOperacaoStore instanteOperacaoStore() {
        return store;
    }

    public void closeResources() {
        if (dbConn != null) {
            try {
                dbConn.close();
                dbConn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
