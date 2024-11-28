package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.model.Subscriptor.Subscriptor;
import ar.edu.utn.dds.k3003.model.Subscriptor.SubscriptorDesperfecto;
import ar.edu.utn.dds.k3003.model.Subscriptor.SubscriptorViandasDisponibles;
import ar.edu.utn.dds.k3003.model.Subscriptor.SubscriptorViandasFaltantes;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;


@Getter
@Setter
@Entity
public class Heladera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String nombre;

    //Cantidad total de viandas
    @Column
    private Integer cantidadDeViandas;
    @OneToMany(mappedBy = "heladeraID")
    Collection<Vianda> viandas;
    @OneToMany(mappedBy = "heladeraId")
    Collection<Temperatura> temperaturas;
    @OneToOne
    SensorMovimiento sensor;
    @OneToOne
    SensorTemperatura sensorTemperatura;
    @OneToOne
    SensorConexion sensorConexion;
    @Column
    private Boolean estaAbierta=true;
    @Column
    private Boolean estaActiva=true;

    @OneToMany(mappedBy = "heladeraID")
     Collection<Retiro> retiros;

    //Subscripciones a heladeras.
    @ManyToMany (mappedBy = "heladerasSuscriptas")
    @JsonManagedReference
    Collection <SubscriptorDesperfecto> subscriptoresDesperfecto;
    @ManyToMany (mappedBy = "heladerasSuscriptas")
    @JsonManagedReference
    Collection <SubscriptorViandasFaltantes> subscriptoresViandasFaltantes;
    @ManyToMany (mappedBy = "heladerasSuscriptas")
    @JsonManagedReference
    Collection <SubscriptorViandasDisponibles> subscriptoresViandasDisponibles;


    public Heladera(String nombre, Integer cantidadDeViandas) {
        this.cantidadDeViandas = cantidadDeViandas;
        this.nombre = nombre;
        this.viandas = new ArrayList<>();
        this.temperaturas = new ArrayList<>();
        this.subscriptoresViandasFaltantes= new ArrayList<>();
        this.subscriptoresViandasDisponibles= new ArrayList<>();
        this.subscriptoresDesperfecto= new ArrayList<>();
        this.retiros= new ArrayList<>();
    }
    protected Heladera(){

    }
    public void guardarVianda(Vianda vianda){
        viandas.add(vianda);
    }

    public void agregarSubscriptorDesperfecto(SubscriptorDesperfecto subscriptor){
        subscriptoresDesperfecto.add(subscriptor);
    }

    public void agregarSubscriptorViandasDisponibles(SubscriptorViandasDisponibles subscriptor){
        subscriptoresViandasDisponibles.add(subscriptor);
    }
    public void agregarSubscriptorViandasFaltantes(SubscriptorViandasFaltantes subscriptor){
        subscriptoresViandasFaltantes.add(subscriptor);
    }

    public void eliminarVianda(Vianda viandaAEliminar){
        Iterator<Vianda> iterator = this.viandas.iterator();
        while (iterator.hasNext()) {
            Vianda vianda = iterator.next();
            if (vianda.getQr().equals(viandaAEliminar.getQr())) {
                iterator.remove(); // Eliminar la vianda si el QR coincide
                System.out.println("Vianda con QR " + viandaAEliminar.getQr() + " eliminada correctamente.");
                return; // Salir del método después de eliminar la vianda
            }
        }
        // Si no se encuentra ninguna vianda con el QR dado
        System.out.println("No se encontró ninguna vianda con el QR " + viandaAEliminar.getQr() + ".");
    }

    public void agregarTemperatura (Temperatura temperatura){
        temperaturas.add(temperatura);
    }
    public Integer getCantViandas(){return viandas.size();}
    public void agregarRetiro (Retiro retiro){
        retiros.add(retiro);
    }

}
