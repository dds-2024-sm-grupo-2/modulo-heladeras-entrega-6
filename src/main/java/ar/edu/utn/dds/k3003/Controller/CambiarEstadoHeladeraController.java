package ar.edu.utn.dds.k3003.Controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.model.Heladera;
import ar.edu.utn.dds.k3003.repositories.HeladeraMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import static java.lang.Integer.parseInt;

public class CambiarEstadoHeladeraController implements Handler{

    private final Fachada fachada;
    private HeladeraMapper mapperHeladera = new HeladeraMapper();
    public CambiarEstadoHeladeraController(Fachada fachada) {
        super();
        this.fachada = fachada;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

    }

    public void cambiarEstadoAbierto (Context ctx) throws Exception {
        try {
            String id = ctx.pathParam("idHeladera");
            Heladera heladera = fachada.getRepoHeladera().findById(parseInt(id));
            if (heladera.getEstaAbierta() == Boolean.FALSE) {
                heladera.setEstaAbierta(Boolean.TRUE);
            } else {
                heladera.setEstaAbierta(Boolean.FALSE);
            }
            fachada.getRepoHeladera().actualizar(heladera);

            ctx.result("Heladera cambiada de estado (estaAbierta)");
            ctx.status(200);
        }
         catch(Exception E){
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result("Error de solicitud");
        }
    }
    public void cambiarEstadoActivo (Context ctx) throws Exception {
        try {
            String id = ctx.pathParam("idHeladera");
            Heladera heladera = fachada.getRepoHeladera().findById(parseInt(id));
            if (heladera.getEstaActiva() == Boolean.FALSE) {
                heladera.setEstaActiva(Boolean.TRUE);
            } else {
                heladera.setEstaActiva(Boolean.FALSE);
            }
            fachada.getRepoHeladera().actualizar(heladera);

            ctx.result("Heladera cambiada de estado (estaActiva)");
            ctx.status(200);
        }
        catch(Exception E){
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result("Error de solicitud");
        }
    }
}
