package br.ucb.prevejo.request;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class VeiculoInstanteSerializer extends StdSerializer<VeiculoInstante> {

    public VeiculoInstanteSerializer() {
        super(VeiculoInstante.class);
    }

    @Override
    public void serialize(VeiculoInstante veiculoInstante, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(veiculoInstante.getVeiculo());
    }

}
