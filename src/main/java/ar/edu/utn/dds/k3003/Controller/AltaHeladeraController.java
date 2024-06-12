package ar.edu.utn.dds.k3003.Controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.dtos.HeladeraDTO;
import ar.edu.utn.dds.k3003.model.Heladera;
import ar.edu.utn.dds.k3003.repositories.HeladeraMapper;
import ar.edu.utn.dds.k3003.repositories.HeladeraRepository;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

public class AltaHeladeraController implements Handler {
    private final Fachada fachada;
    private HeladeraMapper mapperHeladera = new HeladeraMapper();
    public AltaHeladeraController(Fachada fachada) {
        super();
        this.fachada = fachada;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        try {
            HeladeraDTO heladeraDTO = context.bodyAsClass(HeladeraDTO.class);
            var heladeraDTOReturn= fachada.agregar(heladeraDTO);
            context.json(heladeraDTOReturn);
            context.status(HttpStatus.CREATED);
            //context.json("Heladera agregada correctamente");

        } catch (Exception e){
            context.status(HttpStatus.BAD_REQUEST);
            context.result("Error de solicitud");
        }

        }
}
