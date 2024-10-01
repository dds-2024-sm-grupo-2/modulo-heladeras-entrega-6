package ar.edu.utn.dds.k3003.worker;

import ar.edu.utn.dds.k3003.model.Heladera;
import ar.edu.utn.dds.k3003.model.Temperatura;
import ar.edu.utn.dds.k3003.repositories.HeladeraRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.rabbitmq.client.*;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static ar.edu.utn.dds.k3003.app.WebApp.startEntityManagerFactory;

@Setter
public class TemperaturaWorker extends DefaultConsumer {
    private String queueName;
    private EntityManagerFactory entityManagerFactory;

    public TemperaturaWorker(Channel channel, String queueName) {
        super(channel);
        this.queueName = queueName;
        this.entityManagerFactory = entityManagerFactory;
    }

    public TemperaturaWorker(Channel channel) {
        super(channel);
    }
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        // Confirmar la recepción del mensaje a la mensajeria
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        HeladeraRepository repoHeladera = new HeladeraRepository();
        repoHeladera.setEntityManager(entityManager);
        repoHeladera.setEntityManagerFactory(entityManagerFactory);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        this.getChannel().basicAck(envelope.getDeliveryTag(), false);
        String temperaturajson = new String(body, "UTF-8");
        Temperatura temperatura = mapper.readValue(temperaturajson, Temperatura.class);
        repoHeladera.guardarTemperatura(temperatura);
        Heladera heladera = repoHeladera.findById(temperatura.getHeladeraId());
        heladera.agregarTemperatura(temperatura);
        repoHeladera.actualizar(heladera);


        System.out.println("Demora..");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("se recibio el siguiente payload:");
        System.out.println(mapper.writeValueAsString(temperatura));
    }


        public void init() throws IOException {
// Declarar la cola desde la cual consumir mensajes
        this.getChannel().queueDeclare(this.queueName, false, false, false, null);
// Consumir mensajes de la cola
        this.getChannel().basicConsume(this.queueName, false, this);
    }
    public static void main(String[] args) throws IOException, TimeoutException {
        Map<String, String> env = System.getenv();
        ConnectionFactory factory = new ConnectionFactory();
        //factory.setHost(env.get("QUEUE_HOST"));
        factory.setHost("prawn.rmq.cloudamqp.com");
        //factory.setUsername(env.get("QUEUE_USERNAME"));
        factory.setUsername("wcvuathu");
        factory.setVirtualHost("wcvuathu");
        //factory.setPassword(env.get("QUEUE_PASSWORD"));
        factory.setPassword("IkXGMtAKDhWgnR3wIyDCx0eIaBC0xVFt");
        //String queueName = env.get("QUEUE_NAME");
        String queueName = "Temperaturas Queue";

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //EntityManagerFactory entityManagerFactory = startEntityManagerFactory();
        TemperaturaWorker worker = new TemperaturaWorker(channel,queueName);
        //worker.setEntityManagerFactory(entityManagerFactory);
        worker.init();
    }

}
