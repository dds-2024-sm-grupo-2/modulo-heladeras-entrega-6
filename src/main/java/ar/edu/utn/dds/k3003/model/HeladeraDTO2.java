package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.facades.dtos.HeladeraDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeladeraDTO2{
    private String nombre;
    private Integer cantidadTotalDeViandas;

    public HeladeraDTO2(String nombre, Integer cantidadTotalDeViandas) {
        this.nombre=nombre;
        this.cantidadTotalDeViandas = cantidadTotalDeViandas;
    }
    public HeladeraDTO2(){}
}