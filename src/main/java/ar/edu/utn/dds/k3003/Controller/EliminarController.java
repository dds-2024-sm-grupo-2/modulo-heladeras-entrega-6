package ar.edu.utn.dds.k3003.Controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.model.Heladera;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import static java.lang.Integer.parseInt;

public class EliminarController implements Handler {
    private Fachada fachada;

    public EliminarController(Fachada fachada) {
        super();
        this.fachada = fachada;
    }


    @Override
    public void handle(@NotNull Context context) throws Exception {

    }

    public void eliminarHeladeras(Context ctx) {
        fachada.getRepoHeladera().eliminarHeladeras();
        ctx.status(HttpStatus.NO_CONTENT);
    }
    public void eliminarViandas(Context ctx) {
        fachada.getRepoHeladera().eliminarViandas();
        ctx.status(HttpStatus.NO_CONTENT);
    }
    public void eliminarTemperaturas(Context ctx) {
        fachada.getRepoHeladera().eliminarTemperaturas();
        ctx.status(HttpStatus.NO_CONTENT);
    }
    public void obtenerCantidadDeViandas(Context ctx){
        try {
            String id = ctx.pathParam("idHeladera");
            Heladera heladera = fachada.getRepoHeladera().findById(parseInt(id));
            ctx.json(heladera.getCantidadDeViandas()- heladera.getCantViandas());
            ctx.status(HttpStatus.OK);
            //context.result("Heladera obtenida correctamente");
        }catch (Exception e){
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.result("No se encontro el id del heladera");
        }
    }
    public void obtenerRetirosDelDia(Context ctx){
        try {
            String id = ctx.pathParam("idHeladera");
            ctx.json((fachada.getRetirosDelDia(Integer.valueOf(id))).toString());
            ctx.status(HttpStatus.OK);
            //context.result("Heladera obtenida correctamente");
        }catch (Exception e){
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.result("No se encontro el id del heladera");
        }
    }

}
