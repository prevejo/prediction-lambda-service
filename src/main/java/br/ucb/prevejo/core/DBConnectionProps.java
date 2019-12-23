package br.ucb.prevejo.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DBConnectionProps {

    private String url;
    private String user;
    private String pass;
    private String driverClassName;

}
