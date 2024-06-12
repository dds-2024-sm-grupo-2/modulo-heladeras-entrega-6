package ar.edu.utn.dds.k3003.Controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.model.Heladera;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import javax.persistence.criteria.CriteriaBuilder;

import static java.lang.Integer.parseInt;

public class ListaHeladerasController implements Handler {
    private final Fachada fachada;

    public ListaHeladerasController(Fachada fachada) {
        this.fachada = fachada;
    }
    @Override
    public void handle(@NotNull Context context) throws Exception {
        //context.json(fachada.getRepoHeladera().getHeladeras());
        context.json(fachada.getRepoHeladera().findAllHeladeras());
    }
}
