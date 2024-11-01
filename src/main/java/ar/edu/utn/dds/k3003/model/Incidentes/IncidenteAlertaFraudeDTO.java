package ar.edu.utn.dds.k3003.model.Incidentes;

import lombok.Data;


@Data
public class IncidenteAlertaFraudeDTO {
    private Long heladeraId;
    private TipoIncidenteEnum tipoIncidente;

    public IncidenteAlertaFraudeDTO(Long heladeraId, TipoIncidenteEnum tipoIncidente) {
        this.heladeraId = heladeraId;
        this.tipoIncidente = tipoIncidente;
    }
    public IncidenteAlertaFraudeDTO(){

    }
}
