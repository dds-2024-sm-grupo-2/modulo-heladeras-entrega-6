package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.model.Subscriptor.NotificacionDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface colaboradoresRetrofitClient {
    @POST("/evento")
    Call<Void> evento(@Body NotificacionDTO notificacion);
}
