package br.ucb.prevejo.core.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface Resources {

    Connection dbConnectionResource() throws SQLException;
    void closeResources();

}
