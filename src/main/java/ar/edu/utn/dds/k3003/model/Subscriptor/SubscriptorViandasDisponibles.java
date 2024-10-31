package ar.edu.utn.dds.k3003.model.Subscriptor;

import ar.edu.utn.dds.k3003.model.Heladera;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class SubscriptorViandasDisponibles extends Subscriptor {
    @Column
    private Integer nviandas;

   public SubscriptorViandasDisponibles(Long idColaborador,Integer nviandas) {
       super(idColaborador);
       this.nviandas=nviandas;
   }

    public SubscriptorViandasDisponibles() {

    }
    @Override
    public void suscribirse(Heladera heladera) {
        this.agregarHeladera(heladera);
        heladera.agregarSubscriptorViandasDisponibles(this);
    }
}
