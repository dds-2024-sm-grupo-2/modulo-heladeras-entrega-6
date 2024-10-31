package ar.edu.utn.dds.k3003.model.Subscriptor;

import ar.edu.utn.dds.k3003.model.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class SubscriptorViandasFaltantes extends Subscriptor{
    @Column
    private Integer nviandasFaltantes;

    public SubscriptorViandasFaltantes(Long idColaborador,Integer nviandas){
        super(idColaborador);
        this.nviandasFaltantes=nviandas;
    }

    public SubscriptorViandasFaltantes() {

    }
    @Override
    public void suscribirse(Heladera heladera) {
        this.agregarHeladera(heladera);
        heladera.agregarSubscriptorViandasFaltantes(this);
    }
}
