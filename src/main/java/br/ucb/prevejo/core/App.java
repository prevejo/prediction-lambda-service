package br.ucb.prevejo.core;

import br.ucb.prevejo.core.interfaces.Resources;
import br.ucb.prevejo.transporte.instanteoperacao.EnumInstanteOperacaoStore;
import br.ucb.prevejo.transporte.instanteoperacao.InstanteOperacaoDynamoStore;
import com.amazonaws.services.lambda.runtime.Context;

public class App {

    private static final App app = new App();
    private static Context context;
    private static DBConnectionProps dbConnectionProps;
    private static AmazonDynamoDBProps dynamoDBProps;

    private Resources resources;

    private App() {
    }

    public static void useSingletonResources() {
        app.resources = new SingletonResources(
                new JdbcConnectionFactory(dbConnectionProps),
                new DynamoDB(dynamoDBProps),
                EnumInstanteOperacaoStore.valueOf(System.getenv("INST_OP_STORE_TYPE")).getStore());
    }

    public static Resources getResources() {
        return app.resources;
    }

    public static void setContext(Context context) {
        App.context = context;
        App.dbConnectionProps = createDBConnectionProps();
        App.dynamoDBProps = createAmazonDynamoDBProps();
    }

    private static DBConnectionProps createDBConnectionProps() {
        return new DBConnectionProps(
                System.getenv("DB_URL"),
                System.getenv("DB_USER"),
                System.getenv("DB_PASS"),
                "org.postgresql.Driver"
        );
    }

    private static AmazonDynamoDBProps createAmazonDynamoDBProps() {
        return new AmazonDynamoDBProps(
                System.getenv("DYNAMO_DB_ACESS_KEY"),
                System.getenv("DYNAMO_DB_SECRET_KEY"),
                1,
                5000
        );
    }

    public static void log(String log) {
        context.getLogger().log("<-> " + log + "\n");
    }

    public static void shutdown() {
        getResources().closeResources();
    }
}
