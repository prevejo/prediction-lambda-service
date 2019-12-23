package br.ucb.prevejo.core;

import br.ucb.prevejo.core.interfaces.Resources;
import com.amazonaws.services.lambda.runtime.Context;

import java.util.Map;

public class App {

    private static final App app = new App();
    private static Context context;
    private static DBConnectionProps dbConnectionProps;

    private Resources resources;

    private App(){
    }

    public static void useSingletonResources() {
        app.resources = new SingletonResources(new JdbcConnectionFactory(dbConnectionProps));
    }

    public static Resources getResources() {
        return app.resources;
    }

    public static void setContext(Context context) {
        App.context = context;
        App.dbConnectionProps = createDBConnectionProps();
    }

    private static DBConnectionProps createDBConnectionProps() {
        return new DBConnectionProps(
                System.getenv("DB_URL"),
                System.getenv("DB_USER"),
                System.getenv("DB_PASS"),
                "org.postgresql.Driver"
        );
    }

    public static void log(String log) {
        context.getLogger().log("<-> " + log + "\n");
    }

    public static void shutdown() {
        getResources().closeResources();
    }
}
