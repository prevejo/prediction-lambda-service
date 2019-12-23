package br.ucb.prevejo.core;

import br.ucb.prevejo.core.interfaces.PreparedStatementSetter;
import br.ucb.prevejo.core.interfaces.ResultSetEntityParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Connection {

    private final java.sql.Connection conn;

    public Connection(java.sql.Connection conn) {
        this.conn = conn;
    }

    public <T> Optional<T> queryEntity(String sql, PreparedStatementSetter paramSetter, ResultSetEntityParser<T> parser) throws SQLException  {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

            paramSetter.setParams(ps);

            ps.execute();

            ResultSet rs = null;
            try {
                rs = ps.getResultSet();

                if (rs.next()) {
                    return Optional.of(parser.parse(rs));
                }

                return Optional.empty();
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
    }

    public <T> List<T> queryList(String sql, PreparedStatementSetter paramSetter, ResultSetEntityParser<T> parser) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

            paramSetter.setParams(ps);

            ResultSet rs = null;
            try {
                rs = ps.executeQuery();

                List<T> lista = new ArrayList<>();

                while(rs.next()) {
                    lista.add(parser.parse(rs));
                }

                return lista;
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
    }

}
