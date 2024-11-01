package ar.edu.utn.dds.k3003.model.Incidentes;

import lombok.Data;

@Data
public class IncidenteAlertaTemperaturaDTO {
    private Long heladeraId;
    private TipoIncidenteEnum tipoIncidente;
    private Boolean excedeTemperatura;
    private Integer excesoTemperatura;

    public IncidenteAlertaTemperaturaDTO(Long idHeladera, TipoIncidenteEnum tipoIncidente, Boolean excedeTemperatura, Integer excesotemperatura) {
        this.heladeraId = idHeladera;
        this.tipoIncidente = tipoIncidente;
        this.excesoTemperatura = excesotemperatura;
        this.excedeTemperatura = excedeTemperatura;
    }
    public IncidenteAlertaTemperaturaDTO() {}
}
