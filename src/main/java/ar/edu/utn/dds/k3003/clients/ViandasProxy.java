package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.HttpStatus;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class ViandasProxy implements FachadaViandas {
    private final String endpoint;
    private final viandasRetrofitClient service;

    public ViandasProxy(ObjectMapper objectMapper) {

        var env = System.getenv();
        this.endpoint = env.getOrDefault("URL_VIANDAS", "http://localhost:8081/");

        var retrofit =
                new Retrofit.Builder()
                        .baseUrl(this.endpoint)
                        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                        .build();

        this.service = retrofit.create(viandasRetrofitClient.class);
    }

    @Override
    public ViandaDTO agregar(ViandaDTO viandaDTO) {
        return null;
    }

    @Override
    public ViandaDTO modificarEstado(String s, EstadoViandaEnum estadoViandaEnum) throws NoSuchElementException {
        return null;
    }

    @Override
    public List<ViandaDTO> viandasDeColaborador(Long aLong, Integer integer, Integer integer1) throws NoSuchElementException {
        return List.of();
    }

    @Override
    public ViandaDTO buscarXQR(String s) throws NoSuchElementException {
        Response<ViandaDTO> execute = null;
        try {
            execute = service.get(s).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (execute.isSuccessful()) {
            return execute.body();
        }
        if (execute.code() == HttpStatus.NOT_FOUND.getCode()) {
            throw new NoSuchElementException("no se encontro la vianda " + s);
        }
        throw new RuntimeException("Error conectandose con el componente viandas");
    }

    @Override
    public void setHeladerasProxy(FachadaHeladeras fachadaHeladeras) {

    }

    @Override
    public boolean evaluarVencimiento(String s) throws NoSuchElementException {
        return false;
    }

    @Override
    public ViandaDTO modificarHeladera(String s, int i) {
        return null;
    }
}