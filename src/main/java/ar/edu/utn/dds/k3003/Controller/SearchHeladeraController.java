package ar.edu.utn.dds.k3003.Controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.model.Heladera;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import static java.lang.Integer.parseInt;

public class SearchHeladeraController implements Handler {
    private Fachada fachada;
    public SearchHeladeraController(Fachada fachada) {
        super();
        this.fachada = fachada;
    }


    @Override
    public void handle(@NotNull Context context) throws Exception {
        //Json espera un Object y lo convierte en JSON.
        try {
            String id = context.pathParam("idHeladera");
            Heladera heladera = fachada.getRepoHeladera().findById(parseInt(id));
            context.json(heladera);
            context.status(HttpStatus.OK);
            //context.result("Heladera obtenida correctamente");
        }catch (Exception e){
            context.status(HttpStatus.NOT_FOUND);
            context.result("No se encontro el id del heladera");
        }
    }
}
