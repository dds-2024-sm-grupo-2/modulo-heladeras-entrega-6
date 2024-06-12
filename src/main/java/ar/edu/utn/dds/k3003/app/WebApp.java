package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.Controller.*;
import ar.edu.utn.dds.k3003.clients.ViandasProxy;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ar.edu.utn.dds.k3003.facades.dtos.Constants;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class WebApp {
    public static void main(String[] args) {
        Integer port = Integer.parseInt(System.getProperty("port","8080"));
        Javalin app = Javalin.create().start(port);
        var fachada= new Fachada();
        var objectMapper = createObjectMapper();
        fachada.setViandasProxy(new ViandasProxy(objectMapper));
        app.get("/", ctx -> ctx.result("Hola"));
        app.post("/heladeras", new AltaHeladeraController(fachada));
        app.get("/heladeras/{idHeladera}", new SearchHeladeraController(fachada));
        app.get("/heladeras", new ListaHeladerasController(fachada));
        app.post("/temperaturas", new RegistrarTemperaturasController(fachada));
        app.get("/heladeras/{idHeladera}/temperaturas", new ObtenerTemperaturasController(fachada));
        app.post("/depositos", new DepositarViandaController(fachada));
        app.post("/retiros", new RetirarViandaController(fachada));
    }

    public static ObjectMapper createObjectMapper() {
        var objectMapper = new ObjectMapper();
        configureObjectMapper(objectMapper);
        return objectMapper;
    }

    public static void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var sdf = new SimpleDateFormat(Constants.DEFAULT_SERIALIZATION_FORMAT, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        objectMapper.setDateFormat(sdf);
    }
}
