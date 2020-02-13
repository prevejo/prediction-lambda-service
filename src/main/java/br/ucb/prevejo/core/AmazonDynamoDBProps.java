package br.ucb.prevejo.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AmazonDynamoDBProps {

    private String acessKey;
    private String secretKey;
    private Integer maxConnections;
    private Integer connectionTimeout;

}
