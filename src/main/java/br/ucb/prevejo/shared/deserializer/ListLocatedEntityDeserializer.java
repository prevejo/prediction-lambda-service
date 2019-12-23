package br.ucb.prevejo.shared.deserializer;

import br.ucb.prevejo.shared.model.ListLocatedEntity;
import br.ucb.prevejo.shared.util.Collections;
import br.ucb.prevejo.shared.util.Geo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import org.locationtech.jts.geom.Point;

import java.io.IOException;
import java.util.List;

public class ListLocatedEntityDeserializer extends StdDeserializer<ListLocatedEntity> {

    public ListLocatedEntityDeserializer() {
        this(ListLocatedEntity.class);
    }

    public ListLocatedEntityDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ListLocatedEntity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        List<Point> path = Collections.toList(Collections.mapIterator(
                node.iterator(),
                jn -> Geo.makePointXY(
                        ((DoubleNode)jn.get(0)).doubleValue(),
                        ((DoubleNode)jn.get(1)).doubleValue()
                )));

        return new ListLocatedEntity(path);
    }

}
