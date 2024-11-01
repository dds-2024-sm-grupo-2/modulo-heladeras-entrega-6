package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.model.Incidentes.IncidenteAlertaConexionDTO;
import ar.edu.utn.dds.k3003.model.Incidentes.IncidenteAlertaFraudeDTO;
import ar.edu.utn.dds.k3003.model.Incidentes.IncidenteAlertaTemperaturaDTO;
import ar.edu.utn.dds.k3003.model.Incidentes.IncidenteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.HttpStatus;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.NoSuchElementException;

public class IncidentesProxy {

    private final String endpoint;
    private final incidentesRetrofitClient service;

    public IncidentesProxy(ObjectMapper objectMapper) {

        var env = System.getenv();
        this.endpoint = env.getOrDefault("URL_INCIDENTES", "http://localhost:8081/");

        var retrofit =
                new Retrofit.Builder()
                        .baseUrl(this.endpoint)
                        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                        .build();

        this.service = retrofit.create(incidentesRetrofitClient.class);
    }

    public IncidenteDTO crearIncidenteFraude(IncidenteAlertaFraudeDTO incidente) {
        Response<IncidenteDTO> execute = null;
        try {
            execute = service.crearIncidenteFraude(incidente).execute();
            if (!execute.isSuccessful()) {
                throw new NoSuchElementException("Error no se pudo crear el incidente");
            }
            if (execute.code() == HttpStatus.NOT_FOUND.getCode()) {
                throw new NoSuchElementException("Error no se crear el incidente");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error de QR", e);
        }
        return execute.body();
    }
    public IncidenteDTO crearIncidenteAlertaTemperatura(IncidenteAlertaTemperaturaDTO incidente) {
        Response<IncidenteDTO> execute = null;
        try {
            execute = service.crearIncidenteAlertaTemperatura(incidente).execute();
            if (!execute.isSuccessful()) {
                throw new NoSuchElementException("Error no se pudo crear el incidente");
            }
            if (execute.code() == HttpStatus.NOT_FOUND.getCode()) {
                throw new NoSuchElementException("Error no se crear el incidente");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error de QR", e);
        }
        return execute.body();
    }
    public IncidenteDTO crearIncidenteConexion(IncidenteAlertaConexionDTO incidente) {
        Response<IncidenteDTO> execute = null;
        try {
            execute = service.crearIncidenteAlertaConexion(incidente).execute();
            if (!execute.isSuccessful()) {
                throw new NoSuchElementException("Error no se pudo crear el incidente");
            }
            if (execute.code() == HttpStatus.NOT_FOUND.getCode()) {
                throw new NoSuchElementException("Error no se crear el incidente");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error de QR", e);
        }
        return execute.body();
    }
}
