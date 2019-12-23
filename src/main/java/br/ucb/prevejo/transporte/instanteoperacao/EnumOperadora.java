package br.ucb.prevejo.transporte.instanteoperacao;

public enum EnumOperadora {

    MARECHAL("Marechal", "gps.marechal"),
    URBI("Urbi", "gps.urbi"),
    SAO_JOSE("SÃ£o José", "gps.saojose"),
    PIRACICABANA("Piracicabana", "gps.piracicabana"),
    PIONEIRA("Pioneira", "gps.pioneira");

    private String descricao;
    private String webServiceConfigId;

    private EnumOperadora(String desc, String webServiceConfigId) {
        this.descricao = desc;
        this.webServiceConfigId = webServiceConfigId;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getWebServiceConfigId() {
        return webServiceConfigId;
    }
}
