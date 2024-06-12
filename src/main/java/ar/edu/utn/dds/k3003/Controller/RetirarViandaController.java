package ar.edu.utn.dds.k3003.Controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.dtos.RetiroDTO;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class RetirarViandaController implements Handler {
    private final Fachada fachada;
    public RetirarViandaController(Fachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        try {
            RetiroDTO retiroDTO = context.bodyAsClass(RetiroDTO.class);
            fachada.retirar(retiroDTO);
            context.status(200);
            context.result("Vianda retirada correctamente");
        }
        catch (Exception e) {
            context.status(400);
            context.result("Error de solicitud");

        }
    }
}
