package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.model.Incidentes.IncidenteAlertaConexionDTO;
import ar.edu.utn.dds.k3003.model.Incidentes.IncidenteAlertaFraudeDTO;
import ar.edu.utn.dds.k3003.model.Incidentes.IncidenteAlertaTemperaturaDTO;
import ar.edu.utn.dds.k3003.model.Incidentes.IncidenteDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface incidentesRetrofitClient {
    @POST("/incidentes")
    Call<IncidenteDTO> crearIncidenteFraude(@Body IncidenteAlertaFraudeDTO incidente);

    @POST("/incidentes")
    Call<IncidenteDTO> crearIncidenteAlertaTemperatura(@Body IncidenteAlertaTemperaturaDTO incidente);

    @POST("/incidentes")
    Call<IncidenteDTO> crearIncidenteAlertaConexion(@Body IncidenteAlertaConexionDTO incidente);
}
