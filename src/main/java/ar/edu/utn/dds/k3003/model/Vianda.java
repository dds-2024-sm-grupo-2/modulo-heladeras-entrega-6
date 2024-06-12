package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Vianda {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String qr;
    @Column
    private Long colaboradorID;
    @Column
    private Long heladeraID;
    @Column
    private EstadoViandaEnum estado;
    @Column (columnDefinition = "Date")
    private LocalDateTime fechaElaboracion;

    public Vianda(String qr, Long heladeraID, EstadoViandaEnum estado, Long colaboradorID, LocalDateTime fechaElaboracion) {
        this.qr = qr;
        this.heladeraID = heladeraID;
        this.estado = estado;
        this.colaboradorID = colaboradorID;
        this.fechaElaboracion = fechaElaboracion;
    }
    public Vianda(){

    }
}
