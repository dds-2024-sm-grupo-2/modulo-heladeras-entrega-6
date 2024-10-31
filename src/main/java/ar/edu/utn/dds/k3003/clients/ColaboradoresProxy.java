package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.facades.FachadaColaboradores;
import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.model.Subscriptor.NotificacionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class ColaboradoresProxy implements FachadaColaboradores {
    private final String endpoint;
    private final colaboradoresRetrofitClient service;

    public ColaboradoresProxy(ObjectMapper objectMapper) {

        var env = System.getenv();
        this.endpoint = env.getOrDefault("URL_COLABORADORES", "http://localhost:8081/");

        var retrofit =
                new Retrofit.Builder()
                        .baseUrl(this.endpoint)
                        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                        .build();

        this.service = retrofit.create(colaboradoresRetrofitClient.class);
    }

    public void evento(NotificacionDTO notificacion) {
        Response<Void> execute = null;
        try {
            execute = service.evento(notificacion).execute();
            if (!execute.isSuccessful()) {
                throw new NoSuchElementException("Error no se pudo enviar notificación: " + notificacion.toString());
            }
            if (execute.code() == HttpStatus.NOT_FOUND.getCode()) {
                throw new NoSuchElementException("Error no se pudo enviar notificación: " + notificacion.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error de QR", e);
        }
    }


    @Override
    public ColaboradorDTO agregar(ColaboradorDTO colaboradorDTO) {
        return null;
    }

    @Override
    public ColaboradorDTO buscarXId(Long aLong) throws NoSuchElementException {
        return null;
    }

    @Override
    public Double puntos(Long aLong) throws NoSuchElementException {
        return 0.0;
    }

    @Override
    public ColaboradorDTO modificar(Long aLong, List<FormaDeColaborarEnum> list) throws NoSuchElementException {
        return null;
    }

    @Override
    public void actualizarPesosPuntos(Double aDouble, Double aDouble1, Double aDouble2, Double aDouble3, Double aDouble4) {

    }

    @Override
    public void setLogisticaProxy(FachadaLogistica fachadaLogistica) {

    }

    @Override
    public void setViandasProxy(FachadaViandas fachadaViandas) {

    }
}
