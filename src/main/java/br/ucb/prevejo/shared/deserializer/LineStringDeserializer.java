package br.ucb.prevejo.shared.deserializer;

import br.ucb.prevejo.shared.util.Geo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

import java.io.IOException;

public class LineStringDeserializer extends StdDeserializer<LineString> {

    public LineStringDeserializer() {
        super(Point.class);
    }

    @Override
    public LineString deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TreeNode tree = jsonParser.readValueAsTree();

        TreeNode treeNode = tree.get("coordinates");

        int size = treeNode.size();

        Coordinate[] coords = new Coordinate[size];

        for (int i = 0; i < size; i++) {
            TreeNode coord = treeNode.get(i);

            coords[i] = new Coordinate(
                    Double.valueOf(coord.get(0).toString()),
                    Double.valueOf(coord.get(1).toString())
            );
        }

        return Geo.toLineString(coords);
    }

}
