package ar.edu.utn.dds.k3003.Controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.HeladeraDTO;
import ar.edu.utn.dds.k3003.model.Heladera;
import ar.edu.utn.dds.k3003.model.HeladeraSensor;
import ar.edu.utn.dds.k3003.model.SensorConexion;
import ar.edu.utn.dds.k3003.model.SensorMovimiento;
import ar.edu.utn.dds.k3003.repositories.HeladeraMapper;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import retrofit2.http.HTTP;

import static java.lang.Integer.parseInt;

public class SensorController implements Handler{
    private final Fachada fachada;
    private HeladeraMapper heladeraMapper= new HeladeraMapper();
    public SensorController(Fachada fachada) {
        super();
        this.fachada = fachada;
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        try {
            String id = ctx.pathParam("idHeladera");
            Heladera heladera = fachada.getRepoHeladera().findById(parseInt(id));
            SensorMovimiento sensorMovimiento = fachada.activarSensorMovimiento(heladera);
            HeladeraSensor heladeraSensor = new HeladeraSensor(heladera.getId(),sensorMovimiento.getEstado());
            ctx.json("Sensor de movimiento activado");
            ctx.status(HttpStatus.OK);
        } catch (Exception e){
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result("Error de solicitud");
        }

    }

    public void activarSensorConexion(@NotNull Context ctx){
        try {
            String id = ctx.pathParam("idHeladera");
            Heladera heladera = fachada.getRepoHeladera().findById(parseInt(id));
            SensorConexion sensorConexion = fachada.activarSensorConexion(heladera);
            ctx.json("Sensor de conexion activado");
            ctx.status(HttpStatus.OK);
        } catch (Exception e){
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result("Error de solicitud");
        }
    }

}
