package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.facades.dtos.TemperaturaDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Temperatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTemperatura;

    @Column
    private  Integer heladeraId;
    @Column(columnDefinition = "Date")
    private  LocalDateTime fechaMedicion;
    @Column
    private  Integer temperatura;

    public Temperatura(Integer heladeraId, LocalDateTime fechaMedicion, Integer temperatura) {
        this.heladeraId = heladeraId;
        this.fechaMedicion = fechaMedicion;
        this.temperatura = temperatura;
    }
    public Temperatura(){

    }
}

