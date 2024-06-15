package ar.edu.utn.dds.k3003.Controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

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
}
