package br.ucb.prevejo.core;

import br.ucb.prevejo.core.interfaces.ConnectionFactory;
import br.ucb.prevejo.core.interfaces.Resources;

import java.sql.Connection;
import java.sql.SQLException;

public class SingletonResources implements Resources {

    private ConnectionFactory dbConnectionFactory;
    private Connection dbConn;

    public SingletonResources(ConnectionFactory dbConnectionFactory){
        this.dbConnectionFactory = dbConnectionFactory;
    }

    public Connection dbConnectionResource() throws SQLException {
        if (dbConn == null ||  dbConn.isClosed()) {
            dbConn = dbConnectionFactory.createConnection();
        }

        return dbConn;
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
