package br.ucb.prevejo.shared.deserializer;

import br.ucb.prevejo.shared.util.Geo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.locationtech.jts.geom.Point;

import java.io.IOException;

public class PointDeserializer extends StdDeserializer<Point> {

    public PointDeserializer() {
        super(Point.class);
    }

    @Override
    public Point deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TreeNode treeNode = jsonParser.readValueAsTree();
        return Geo.makePointXY(
            Double.valueOf(treeNode.get("coordinates").get(0).toString()),
            Double.valueOf(treeNode.get("coordinates").get(1).toString())
        );
    }

}
