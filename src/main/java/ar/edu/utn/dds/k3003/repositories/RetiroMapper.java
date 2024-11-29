package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.facades.dtos.HeladeraDTO;
import ar.edu.utn.dds.k3003.facades.dtos.RetiroDTO;
import ar.edu.utn.dds.k3003.model.Heladera;
import ar.edu.utn.dds.k3003.model.Retiro;

public class RetiroMapper {

    public static RetiroDTO map(Retiro retiro){
        RetiroDTO dto = new RetiroDTO(retiro.getQrVianda(), retiro.getTarjeta(), Math.toIntExact(retiro.getHeladeraID()));
        dto.setId(retiro.getId());
        return dto;
    }
    public RetiroMapper() {
    }
}
