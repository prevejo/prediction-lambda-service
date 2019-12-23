package br.ucb.prevejo.core.interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter {
    void setParams(PreparedStatement ps) throws SQLException;
}
