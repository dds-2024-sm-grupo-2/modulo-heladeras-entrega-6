package ar.edu.utn.dds.k3003.model.Subscriptor;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
public class NotificacionDTO {
    private List<Long> idColab= new ArrayList<>();
    private String msg;

    public NotificacionDTO(List<Long> idColab, String msg) {
        this.idColab = idColab;
        this.msg = msg;
    }
    public NotificacionDTO(){

    }
}
