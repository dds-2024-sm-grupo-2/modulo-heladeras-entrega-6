package ar.edu.utn.dds.k3003.Controller;
import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.dtos.TemperaturaDTO;
import ar.edu.utn.dds.k3003.model.Temperatura;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ObtenerTemperaturasController implements Handler {
    private Fachada fachada;
    public ObtenerTemperaturasController(Fachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        try {
            String id = context.pathParam("idHeladera");
            List<TemperaturaDTO> temperaturas = fachada.obtenerTemperaturas(Integer.valueOf(id));
            context.status(200);
            context.json(temperaturas);
        } catch (Exception e) {
            context.status(404);
            context.result("Heladera no encontrado");
        }

        }
}
