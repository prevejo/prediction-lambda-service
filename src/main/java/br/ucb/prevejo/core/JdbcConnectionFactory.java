package br.ucb.prevejo.core;

import br.ucb.prevejo.core.interfaces.ConnectionConsumer;
import br.ucb.prevejo.core.interfaces.ConnectionFactory;

import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionFactory implements ConnectionFactory {

    private final DBConnectionProps props;

    public JdbcConnectionFactory(DBConnectionProps props) {
        this.props = props;
    }

    public java.sql.Connection createConnection() throws SQLException {
        try {
            Class.forName(props.getDriverClassName());

            return DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPass());
        } catch(ClassNotFoundException e) {
            throw new SQLException("DB Driver not found");
        }
    }

    public static <T> T requestConnection(ConnectionConsumer<T> consumer) {
        try {
            return consumer.accept(new Connection(App.getResources().dbConnectionResource()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
