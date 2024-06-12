package ar.edu.utn.dds.k3003.Controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.model.ViandaRequest;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class DepositarViandaController implements Handler {
    private final Fachada fachada;

    public DepositarViandaController(Fachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        try {
            ViandaRequest viandaRequest = context.bodyAsClass(ViandaRequest.class);
            fachada.depositar(viandaRequest.getHeladeraId(), viandaRequest.getQrVianda());
            context.status(200);
            context.result("Vianda depositada correctamente");
        }
        catch (Exception e) {
            context.status(400);
            context.result("Error de solicitud");
        }
        }
}
