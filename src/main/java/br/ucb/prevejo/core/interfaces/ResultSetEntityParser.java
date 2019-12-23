package br.ucb.prevejo.core.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetEntityParser<T> {
    T parse(ResultSet rs) throws SQLException;
}
