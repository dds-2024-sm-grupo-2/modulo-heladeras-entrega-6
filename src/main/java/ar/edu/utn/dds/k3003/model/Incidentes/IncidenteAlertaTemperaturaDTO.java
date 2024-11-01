package ar.edu.utn.dds.k3003.model.Incidentes;

import lombok.Data;

@Data
public class IncidenteAlertaTemperaturaDTO {
    private Long idHeladera;
    private TipoIncidenteEnum tipoIncidente;
    private Boolean excedeTemperatura;
    private Integer excesotemperatura;

    public IncidenteAlertaTemperaturaDTO(Long idHeladera, TipoIncidenteEnum tipoIncidente, Boolean excedeTemperatura, Integer excesotemperatura) {
        this.idHeladera = idHeladera;
        this.tipoIncidente = tipoIncidente;
        this.excesotemperatura = excesotemperatura;
        this.excedeTemperatura = excedeTemperatura;
    }
    public IncidenteAlertaTemperaturaDTO() {}
}
