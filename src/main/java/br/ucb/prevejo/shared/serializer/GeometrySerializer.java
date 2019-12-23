package br.ucb.prevejo.shared.serializer;

import br.ucb.prevejo.shared.util.Geo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;

public class GeometrySerializer extends StdSerializer<Geometry> {

    public GeometrySerializer() {
        this(Geometry.class);
    }

    public GeometrySerializer(Class<Geometry> t) {
        super(t);
    }

    @Override
    public void serialize(Geometry geometry, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeRawValue(Geo.toGeoJson(geometry));
    }

}

