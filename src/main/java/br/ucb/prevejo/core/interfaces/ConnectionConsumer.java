package br.ucb.prevejo.core.interfaces;

import br.ucb.prevejo.core.Connection;

import java.sql.SQLException;

public interface ConnectionConsumer<R> {

    R accept(Connection conn) throws SQLException;

}
