package ar.edu.utn.dds.k3003.Controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class DepositoViandaController implements Handler {
    private Fachada fachada;
    private FachadaViandas fachadaViandas;
    public DepositoViandaController(Fachada fachada) {
        super();
        this.fachada = fachada;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        String id = context.pathParam("id");
        //Heladera heladera = repo.findById(Integer.parseInt(id));
        //repo.remove(heladera);
        context.result("eliminada");
    }
}
