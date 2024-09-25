package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.model.Vianda;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface viandasRetrofitClient {

        @GET("viandas/{qr}")
        Call<ViandaDTO> get (@Path("qr") String qr);

        @PATCH("viandas/{qr}/estado")
        Call<ViandaDTO> modificarEstado(@Path("qr") String qr, @Body EstadoViandaEnum estado);
}
