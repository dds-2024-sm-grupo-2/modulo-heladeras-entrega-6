package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.model.Vianda;

public class ViandaMapper {
    public ViandaDTO map(Vianda vianda) {
        return new ViandaDTO(vianda.getQr(),vianda.getFechaElaboracion(),vianda.getEstado(),vianda.getColaboradorID(), Math.toIntExact(vianda.getHeladeraID()));
    }
}
