package br.ucb.prevejo.core;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class DynamoDB {

    private com.amazonaws.services.dynamodbv2.document.DynamoDB dynamoDB;

    public DynamoDB(AmazonDynamoDBProps props) {
        this.dynamoDB = buildDB(props);
    }

    public static DynamoDBRequest requestTable(String tableName) {
        return new DynamoDBRequest(App.getResources().dynamoDBResource().dynamoDB.getTable(tableName));
    }

    private com.amazonaws.services.dynamodbv2.document.DynamoDB buildDB(AmazonDynamoDBProps props) {
        AmazonDynamoDB client = AmazonDynamoDBClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                        props.getAcessKey(),
                        props.getSecretKey()
                )))
                .withRegion(Regions.SA_EAST_1)
                .withClientConfiguration(new ClientConfiguration()
                        .withMaxConnections(props.getMaxConnections())
                        .withConnectionTimeout(props.getConnectionTimeout()))
                .build();

        return new com.amazonaws.services.dynamodbv2.document.DynamoDB(client);
    }

}
