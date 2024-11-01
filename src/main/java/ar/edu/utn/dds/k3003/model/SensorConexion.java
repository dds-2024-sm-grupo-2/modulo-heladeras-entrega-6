package ar.edu.utn.dds.k3003.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter

public class SensorConexion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSensor;
    @Column
    private Boolean estado=Boolean.FALSE;

    public SensorConexion() {

    }
}
