package ar.edu.utn.dds.k3003.model.Incidentes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class IncidenteAlertaConexionDTO {
    private Long heladeraId;
    private TipoIncidenteEnum tipoIncidente;
    private Integer tiempoSinRespuesta;

    public IncidenteAlertaConexionDTO(Long heladeraId, TipoIncidenteEnum tipoIncidente, Integer tiempoSinRespuesta) {
        this.heladeraId = heladeraId;
        this.tipoIncidente = tipoIncidente;
        this.tiempoSinRespuesta = tiempoSinRespuesta;
    }
}
