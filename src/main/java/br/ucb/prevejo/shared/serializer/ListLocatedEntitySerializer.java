package br.ucb.prevejo.shared.serializer;

import br.ucb.prevejo.shared.model.ListLocatedEntity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.locationtech.jts.geom.Point;

import java.io.IOException;

public class ListLocatedEntitySerializer extends StdSerializer<ListLocatedEntity> {

    public ListLocatedEntitySerializer() {
        this(ListLocatedEntity.class);
    }

    public ListLocatedEntitySerializer(Class<ListLocatedEntity> t) {
        super(t);
    }

    @Override
    public void serialize(ListLocatedEntity locatedEntity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();

        for (Point point : locatedEntity.getRecordPath()) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeNumber(point.getX());
            jsonGenerator.writeNumber(point.getY());
            jsonGenerator.writeEndArray();
        }

        jsonGenerator.writeEndArray();
    }

}
