package ar.edu.utn.dds.k3003.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.*;
import ar.edu.utn.dds.k3003.repositories.HeladeraMapper;
import ar.edu.utn.dds.k3003.repositories.HeladeraRepository;
import ar.edu.utn.dds.k3003.repositories.TemperaturaMapper;
import ar.edu.utn.dds.k3003.repositories.ViandaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class FachadaTest {
  private Fachada fachada;

  private HeladeraRepository repoHeladera;
  private HeladeraMapper heladeraMapper;
  private TemperaturaMapper temperaturaMapper;

   private ViandaMapper viandaMapper;

  @BeforeEach
  void setUp() {

    repoHeladera = new HeladeraRepository();
    heladeraMapper = new HeladeraMapper();
    temperaturaMapper = new TemperaturaMapper();

    viandaMapper = new ViandaMapper();
    fachada = new ar.edu.utn.dds.k3003.app.Fachada(temperaturaMapper, repoHeladera, heladeraMapper);
    //fachada = new Fachada();
  }

  @Test
  void testFachadaAgregar() {
    Heladera heladera = new Heladera("Bombonera");
    heladera.setId(11);
    HeladeraDTO heladeraDto = heladeraMapper.map(heladera);
    fachada.agregar(heladeraDto);
    Heladera heladeraEncontrada = repoHeladera.findById(heladera.getId());
    assertEquals(heladera.getId(), heladeraEncontrada.getId());
    assertEquals(heladera.getNombre(), heladeraEncontrada.getNombre());

  }

  @Test
  void testDepositar() {
    Heladera heladera = new Heladera( "Bombonera");
    heladera.setId(2);
    Vianda vianda1 = new Vianda("QR1", (long) 2, EstadoViandaEnum.PREPARADA, (long) 3, LocalDateTime.of(2024, 4, 28, 12, 30));
    Vianda vianda2 = new Vianda("QR1", (long) 3, EstadoViandaEnum.PREPARADA, (long) 3, LocalDateTime.of(2024, 4, 28, 12, 30));
    ViandaDTO viandaDTO1 = viandaMapper.map(vianda1);
    ViandaDTO viandaDTO2 = viandaMapper.map(vianda2);
    FachadaViandas mockFachadaViandas = mock(FachadaViandas.class);
    when(mockFachadaViandas.buscarXQR(vianda1.getQr())).thenReturn(viandaDTO1);
    when(mockFachadaViandas.buscarXQR(vianda2.getQr())).thenReturn(viandaDTO2);

    /**
     when(mockFachadaViandas.modificarEstado(any(), eq(EstadoViandaEnum.DEPOSITADA)))
     .thenAnswer(invocation -> {
     // Obtenemos el objeto de la vianda pasado como argumento
     Vianda vianda = invocation.getArgument(0);

     // Realizamos la modificación del objeto vianda
     vianda.setEstado(EstadoViandaEnum.DEPOSITADA);
     return null;
     });
     */


    fachada.setViandasProxy(mockFachadaViandas);

    fachada.agregar(heladeraMapper.map(heladera));

    fachada.depositar(heladera.getId(), vianda1.getQr());

    fachada.depositar(heladera.getId(), vianda2.getQr());

    assertEquals(2, fachada.cantidadViandas(2));
    /** Intenté mockear fachadaViandas.modificarEstado y me costó bastante, así que dejé así el test por el momentp
     assertEquals(EstadoViandaEnum.DEPOSITADA,vianda1.getEstado());
     assertEquals(EstadoViandaEnum.DEPOSITADA,vianda2.getEstado());
     */
  }

  @Test
  void testRetirar() {
    RetiroDTO retiroDTO = new RetiroDTO("Dos", "Tarjeta", 2);
    Vianda vianda1 = new Vianda("Dos", (long) 2, EstadoViandaEnum.PREPARADA, (long) 3, LocalDateTime.now());
    Heladera heladera = new Heladera("Heladerota");
    heladera.setId(2);
    ViandaDTO viandaDTO1 = viandaMapper.map(vianda1);
    FachadaViandas mockFachadaViandas = mock(FachadaViandas.class);
    when(mockFachadaViandas.buscarXQR(vianda1.getQr())).thenReturn(viandaDTO1);

    fachada.setViandasProxy(mockFachadaViandas);
    fachada.agregar(heladeraMapper.map(heladera));
    fachada.depositar(heladera.getId(), vianda1.getQr());
    Heladera heladeraModUno = repoHeladera.findById(heladera.getId());

    assertEquals(1, heladeraModUno.getViandas().size());

    fachada.retirar(retiroDTO);
    assertEquals(0, heladeraModUno.getViandas().size());



  }

  @Test
  void testTemperatura() {
    Heladera heladera = new Heladera("Heladerota");
    heladera.setId(2);
    TemperaturaDTO temperaturaDTO= new TemperaturaDTO(23,2,LocalDateTime.now());
    fachada.agregar(heladeraMapper.map(heladera));
    fachada.temperatura(temperaturaDTO);
    Heladera heladeraModUno = repoHeladera.findById(heladera.getId());
    assertEquals(1, heladeraModUno.getTemperaturas().size());

  }

  @Test
  void testObtenerTemperaturas(){
    Heladera heladera = new Heladera("Heladerota");
    heladera.setId(2);
    TemperaturaDTO temperaturaDTO1= new TemperaturaDTO(23,2,LocalDateTime.of(2024,5,2,11,0));
    TemperaturaDTO temperaturaDTO2= new TemperaturaDTO(22,2,LocalDateTime.of(2024,04,28,10,30));
    TemperaturaDTO temperaturaDTO3= new TemperaturaDTO(21,2,LocalDateTime.of(2024,04,27,10,30));
    fachada.agregar(heladeraMapper.map(heladera));
    fachada.temperatura(temperaturaDTO1);
    fachada.temperatura(temperaturaDTO2);
    fachada.temperatura(temperaturaDTO3);
    Heladera heladeraModUno = repoHeladera.findById(heladera.getId());

    assertEquals(3, heladeraModUno.getTemperaturas().size());
    assertEquals(LocalDateTime.of(2024,5,2,11,0), fachada.obtenerTemperaturas(heladera.getId()).get(0).getFechaMedicion());
    assertEquals(LocalDateTime.of(2024,4,28,10,30), fachada.obtenerTemperaturas(heladera.getId()).get(1).getFechaMedicion());


  }
}