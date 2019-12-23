package br.ucb.prevejo;

import br.ucb.prevejo.core.App;
import br.ucb.prevejo.estimativa.EstimativaService;
import br.ucb.prevejo.estimativa.model.EstimativaPercurso;
import br.ucb.prevejo.request.Request;
import br.ucb.prevejo.request.VeiculoInstanteSerializer;
import br.ucb.prevejo.shared.deserializer.LocalDateTimeDeserializer;
import br.ucb.prevejo.shared.interfaces.LocatedEntity;
import br.ucb.prevejo.shared.serializer.GeometrySerializer;
import br.ucb.prevejo.shared.serializer.TimeSerializer;
import br.ucb.prevejo.transporte.parada.Parada;
import br.ucb.prevejo.transporte.parada.ParadaService;
import br.ucb.prevejo.transporte.percurso.EnumSentido;
import br.ucb.prevejo.transporte.percurso.Percurso;
import br.ucb.prevejo.transporte.percurso.PercursoService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.locationtech.jts.geom.Point;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class Main implements RequestStreamHandler {

    private ObjectMapper objectMapper = createMapper();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        Request request = objectMapper.readValue(inputStream, Request.class);

        App.setContext(context);
        App.useSingletonResources();

        try {
            Optional<Percurso> percursoOp = PercursoService.instance().obterPercursoFetchLinha(
                    request.getNumero(),
                    EnumSentido.valueOf(request.getSentido())
            );
            Optional<Parada> paradaOp = ParadaService.instance().obterPorCodigo(request.getParada());

            Collection<? extends LocatedEntity> veiculos = request.getVeiculos();

            objectMapper.writeValue(
                    outputStream,
                    percursoOp.flatMap(percurso -> paradaOp.map(parada -> response(percurso, parada, veiculos)))
                    .orElse(null)
            );
        } finally {
            App.shutdown();
        }
    }

    private EstimativaPercurso response(Percurso percurso, Parada embarque, Collection<? extends LocatedEntity> veiculos) {
        return EstimativaService.instance().estimar(percurso, embarque, veiculos);
    }

    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(new GeometrySerializer());
        module.addSerializer(new TimeSerializer());
        module.addSerializer(new VeiculoInstanteSerializer());
        module.addDeserializer(Point.class, new br.ucb.prevejo.shared.deserializer.PointDeserializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());

        mapper.registerModule(module);

        return mapper;
    }


    /*public static void main(String[] args) throws IOException {
        Main main = new Main();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Request request = new Request();
        request.setNumero("099.1");
        request.setSentido("CIRCULAR");
        request.setParada("4976");
        request.setVeiculos(java.util.Arrays.asList(new ListLocatedEntity(java.util.Arrays.asList(
                br.ucb.prevejo.shared.util.Geo.makePointXY(-48.03951, -15.83668),
                br.ucb.prevejo.shared.util.Geo.makePointXY(-48.03811, -15.83774),
                br.ucb.prevejo.shared.util.Geo.makePointXY(-48.03685, -15.83864),
                br.ucb.prevejo.shared.util.Geo.makePointXY(-48.03572, -15.83966)
        ))));

        main.objectMapper.writeValue(baos, request);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        main.handleRequest(new ByteArrayInputStream(baos.toByteArray()), output, null);

        com.fasterxml.jackson.databind.JsonNode jsonNode = main.objectMapper.readTree(new ByteArrayInputStream(output.toByteArray()));

        System.out.println(jsonNode.get("chegadas").toString());
    }*/

}
