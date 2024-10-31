package ar.edu.utn.dds.k3003.model.Subscriptor;

import ar.edu.utn.dds.k3003.model.Heladera;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Subscriptor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column
    private Long idColaborador;
    @Column
    @ManyToMany
    @JsonBackReference
    private List<Heladera> heladerasSuscriptas;

    public Subscriptor(Long idColaborador){
        this.idColaborador = idColaborador;
        this.heladerasSuscriptas = new ArrayList<>();
    }
    public Subscriptor(){

    }

    public void suscribirse(Heladera heladera){
        this.agregarHeladera(heladera);
    }
    public void agregarHeladera(Heladera h){
        this.heladerasSuscriptas.add(h);
    }
}
