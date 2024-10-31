package ar.edu.utn.dds.k3003.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class SensorMovimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSensor;
    @Column
    public Boolean estado=Boolean.FALSE;

    public SensorMovimiento(){

    }

    public void detectarMovimiento(){
        //acá iria la logica
        this.estado = Boolean.TRUE;
    }
}
