package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.clients.ColaboradoresProxy;
import ar.edu.utn.dds.k3003.facades.FachadaColaboradores;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.*;
import ar.edu.utn.dds.k3003.model.*;
import ar.edu.utn.dds.k3003.model.Subscriptor.*;
import ar.edu.utn.dds.k3003.repositories.HeladeraMapper;
import ar.edu.utn.dds.k3003.repositories.HeladeraRepository;
import ar.edu.utn.dds.k3003.repositories.TemperaturaMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;


import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Transient;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;


@Setter
@Getter

public class Fachada implements ar.edu.utn.dds.k3003.facades.FachadaHeladeras {

    private final HeladeraRepository repoHeladera;
    private final HeladeraMapper heladeraMapper;
    private FachadaViandas fachadaViandas;
    private ColaboradoresProxy fachadaColaboradores;
    private final TemperaturaMapper temperaturaMapper;
    private static AtomicLong seqId = new AtomicLong();

    private Counter heladerasCreadasCounter;
    private Counter viandasEnHeladerasCounter;
    private Counter temperaturasRegistradasCounter;
    private PrometheusMeterRegistry registry;

    public Fachada(TemperaturaMapper temperaturaMapper, HeladeraRepository repoHeladera, HeladeraMapper heladeraMapper) {
        this.temperaturaMapper = temperaturaMapper;
        this.repoHeladera = repoHeladera;
        this.heladeraMapper = heladeraMapper;
    }

    public Fachada() {
        this.repoHeladera = new HeladeraRepository();
        this.heladeraMapper = new HeladeraMapper();
        this.temperaturaMapper = new TemperaturaMapper();
    }


    public HeladeraDTO agregar(@NotNull HeladeraDTO2 heladeraDTO) {
        Heladera heladera = new Heladera(heladeraDTO.getNombre(), heladeraDTO.getCantidadTotalDeViandas());
        SensorMovimiento sensorMovimiento = new SensorMovimiento();
        heladera.setSensor(sensorMovimiento);
        this.repoHeladera.guardarSensor(sensorMovimiento);
        this.repoHeladera.guardar(heladera);
        heladerasCreadasCounter.increment();
        return heladeraMapper.map(heladera);
    }

    @Override
    public HeladeraDTO agregar(HeladeraDTO heladeraDTO) {
        return null;
    }

    /**
     * "El colaborador que creo la vianda, ahora la deja
     * dentro de la heladera"
     * Primero, busca en el repo el id de la heladera,busca la vianda en el repo, después deposita la vianda y le cambia el estado con la fachada
     */
    @Override
    public void depositar(Integer integer, String s) throws NoSuchElementException {
        Heladera heladera = this.repoHeladera.findById(integer);
        ViandaDTO viandaDTO = fachadaViandas.modificarEstado(s, EstadoViandaEnum.DEPOSITADA);
        Vianda vianda = new Vianda(viandaDTO.getCodigoQR(), (long) viandaDTO.getHeladeraId(), viandaDTO.getEstado(), viandaDTO.getColaboradorId(), viandaDTO.getFechaElaboracion());
        heladera.guardarVianda(vianda);
        repoHeladera.guardarVianda(vianda);
        repoHeladera.guardar(heladera);
        repoHeladera.actualizar(heladera);
        viandasEnHeladerasCounter.increment();
        this.chequearViandasDisponibles(heladera);
        this.chequearViandasFaltantes(heladera);
    }

    @Override
    public Integer cantidadViandas(Integer integer) throws NoSuchElementException {
        int cantidadViandas = repoHeladera.obtenerViandasDeHeladera(integer).size();
        Heladera heladera = repoHeladera.findById(integer);
        heladera.setCantidadDeViandas(cantidadViandas);
        repoHeladera.guardar(heladera);
        repoHeladera.actualizar(heladera);
        return cantidadViandas;
    }

    @Override
    public void retirar(RetiroDTO retiroDTO) throws NoSuchElementException {
        Heladera heladera = this.repoHeladera.findById(retiroDTO.getHeladeraId());
        ViandaDTO viandaDTO = this.fachadaViandas.buscarXQR(retiroDTO.getQrVianda());
        Vianda vianda = new Vianda(viandaDTO.getCodigoQR(), (long) viandaDTO.getHeladeraId(), viandaDTO.getEstado(), viandaDTO.getColaboradorId(), viandaDTO.getFechaElaboracion());
        heladera.eliminarVianda(vianda);
        fachadaViandas.modificarEstado(vianda.getQr(), EstadoViandaEnum.RETIRADA);
        repoHeladera.eliminarVianda(vianda);
        repoHeladera.actualizar(heladera);
        this.chequearViandasDisponibles(heladera);
        this.chequearViandasFaltantes(heladera);
    }

    @Override
    public void temperatura(TemperaturaDTO temperaturaDTO) {
        Temperatura temperatura = new Temperatura(temperaturaDTO.getHeladeraId(), temperaturaDTO.getFechaMedicion(), temperaturaDTO.getTemperatura());
        repoHeladera.guardarTemperatura(temperatura);
        Heladera heladera = repoHeladera.findById(temperatura.getHeladeraId());
        heladera.agregarTemperatura(temperatura);
        repoHeladera.actualizar(heladera);
        temperaturasRegistradasCounter.increment();
    }

    @Override
    public List<TemperaturaDTO> obtenerTemperaturas(Integer integer) {
        List<TemperaturaDTO> temperaturas = repoHeladera.obtenerTemperaturasDeHeladera(integer).stream().map(t -> temperaturaMapper.map(t)).toList();
        return temperaturas.stream().sorted(Comparator.comparing(TemperaturaDTO::getFechaMedicion).reversed()).toList();
    }

    @Override
    public void setViandasProxy(FachadaViandas fachadaViandas) {
        this.fachadaViandas = fachadaViandas;
    }

    public SensorMovimiento activarSensorMovimiento(Heladera heladera) {
        SensorMovimiento sensorMovimiento = heladera.getSensor();
        sensorMovimiento.setEstado(Boolean.TRUE);
        repoHeladera.actualizar(heladera);
        repoHeladera.actualizarSensor(sensorMovimiento);
        return sensorMovimiento;
    }

    public void setRegistry(PrometheusMeterRegistry registry) {
        this.registry = registry;
        this.heladerasCreadasCounter = Counter.builder("app.heladeras.creadas")
                .description("Numero de heladeras creadas")
                .register(registry);
        this.temperaturasRegistradasCounter = Counter.builder("app.temperaturas.registradas")
                .description("Numero de temperaturas registradas")
                .register(registry);
        this.viandasEnHeladerasCounter = Counter.builder("app.viandas.heladeras")
                .description("Numero de viandas en heladera")
                .register(registry);
    }

    public Collection<Long> chequearViandasDisponibles(Heladera heladera) {
        Integer cantDisponibles = heladera.getCantViandas();
        List<Long> subs = new ArrayList<>();

        for (SubscriptorViandasDisponibles subscriptor : heladera.getSubscriptoresViandasDisponibles()) {
            if (Objects.equals(subscriptor.getNviandas(), cantDisponibles)) {
                subs.add(subscriptor.getIdColaborador());
            }
        }

        if (!subs.isEmpty()) {
            fachadaColaboradores.evento(new NotificacionDTO(subs, "Notificacion para viandas disponibles"));
            return subs;
        } else {
            return null;
        }
    }

    public List<Long> chequearViandasFaltantes(Heladera heladera) {
        Integer cantTotal = heladera.getCantidadDeViandas();
        Integer cantDisponibles = heladera.getCantViandas();
        List<Long> subs = new ArrayList<>();

        for (SubscriptorViandasFaltantes sub : heladera.getSubscriptoresViandasFaltantes()) {
            if (cantTotal - cantDisponibles == sub.getNviandasFaltantes()) {
                subs.add(sub.getIdColaborador());
            }
        }

        if (!subs.isEmpty()) {
            fachadaColaboradores.evento(new NotificacionDTO(subs,"Notificacion para faltante de cantidad de viandas"));
            return subs;
        } else {
            return null;
        }
    }

    public List<Long> chequearDesperfecto(Heladera heladera){
        List<Long> subs = new ArrayList<>();
        for(SubscriptorDesperfecto sub: heladera.getSubscriptoresDesperfecto()){
            subs.add(sub.getIdColaborador());
        }
        if (!subs.isEmpty()) {
            fachadaColaboradores.evento(new NotificacionDTO(subs, "Notificacion para heladera inactiva"));
            return subs;
        } else {
            return null;
        }
    }
}
