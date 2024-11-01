package ar.edu.utn.dds.k3003.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
public class SensorTemperatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSensorTemperatura;

    @Column
    private Boolean estado= Boolean.FALSE;
    @Column
    private Integer ntiempo;
    @Column
    private Integer tempMax;
    @Column
    private Integer tempMin;

    public SensorTemperatura(Integer ntiempo, Integer tempMax, Integer tempMin) {
        this.ntiempo = ntiempo;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }
    public SensorTemperatura(){

    }
}
