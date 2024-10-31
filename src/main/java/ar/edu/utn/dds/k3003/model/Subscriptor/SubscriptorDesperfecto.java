package ar.edu.utn.dds.k3003.model.Subscriptor;


import ar.edu.utn.dds.k3003.model.Heladera;

import javax.persistence.Entity;
@Entity
public class SubscriptorDesperfecto extends Subscriptor{

    public SubscriptorDesperfecto(Long idColaborador){
        super(idColaborador);
    }

    public SubscriptorDesperfecto() {

    }
    @Override
    public void suscribirse(Heladera heladera) {
    this.agregarHeladera(heladera);
    heladera.agregarSubscriptorDesperfecto(this);
    }
}
