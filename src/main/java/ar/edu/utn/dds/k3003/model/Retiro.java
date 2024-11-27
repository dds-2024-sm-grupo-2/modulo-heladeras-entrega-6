package ar.edu.utn.dds.k3003.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Retiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String qrVianda;
    @Column
    private String tarjeta;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    )
    @Column
    private LocalDateTime fechaRetiro;

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    public Retiro(String qrVianda, String tarjeta, Integer heladeraId) {
        this.qrVianda = qrVianda;
        this.tarjeta = tarjeta;
        this.heladera = new Heladera();
        heladera.setId(heladeraId);
        this.fechaRetiro = LocalDateTime.now();
    }

    public Retiro(String qrVianda, String tarjeta, LocalDateTime fechaRetiro, Integer heladeraId) {
        this.qrVianda = qrVianda;
        this.tarjeta = tarjeta;
      //  this.heladeraId = heladeraId;
        this.fechaRetiro = fechaRetiro;
    }
}
