package br.ucb.prevejo.transporte.instanteoperacao;

public enum EnumInstanteOperacaoStore {

    DATA_BASE(new InstanteOperacaoDBStore()),
    DYNAMO_DB(new InstanteOperacaoDynamoStore());

    private InstanteOperacaoStore store;

    private EnumInstanteOperacaoStore(InstanteOperacaoStore store) {
        this.store = store;
    }

    public InstanteOperacaoStore getStore() {
        return store;
    }

}
