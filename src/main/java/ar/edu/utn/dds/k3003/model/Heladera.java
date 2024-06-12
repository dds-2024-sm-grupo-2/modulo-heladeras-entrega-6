package ar.edu.utn.dds.k3003.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;


@Getter
@Setter
@Entity
public class Heladera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String nombre;
    @Column
    private Integer cantidadDeViandas;
    @OneToMany
    Collection <Vianda> viandas;
    @OneToMany
    Collection<Temperatura>temperaturas;

    public Heladera(String nombre) {
        this.nombre = nombre;
        this.viandas = new ArrayList<>();
        this.temperaturas = new ArrayList<>();
    }
    protected Heladera(){

    }
    public void guardarVianda(Vianda vianda){
        viandas.add(vianda);
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
}
