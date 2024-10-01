package ar.edu.utn.dds.k3003.worker;

import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.model.Heladera;
import ar.edu.utn.dds.k3003.model.Temperatura;
import ar.edu.utn.dds.k3003.repositories.HeladeraRepository;
import ar.edu.utn.dds.k3003.repositories.TemperaturaMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.rabbitmq.client.*;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static ar.edu.utn.dds.k3003.app.WebApp.startEntityManagerFactory;

@Setter
public class TemperaturaWorker extends DefaultConsumer {
    private String queueName;
    private EntityManagerFactory entityManagerFactory;
    //private HeladeraRepository repoHeladera;
    private FachadaHeladeras fachada;

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
        TemperaturaMapper temperaturaMapper = new TemperaturaMapper();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String temperaturajson = new String(body, StandardCharsets.UTF_8);

        try {
            // Deserializar el JSON a un objeto Temperatura
            Temperatura temperatura = mapper.readValue(temperaturajson, Temperatura.class);

            // Procesar la temperatura
            fachada.temperatura(temperaturaMapper.map(temperatura));

            // Confirmar la recepción del mensaje después de procesarlo
            this.getChannel().basicAck(envelope.getDeliveryTag(), false);

            System.out.println("Se recibió el siguiente payload:");
            System.out.println(mapper.writeValueAsString(temperatura));
        } catch (JsonProcessingException e) {
            System.err.println("Error de procesamiento del JSON: " + e.getMessage());
            e.printStackTrace();
            this.getChannel().basicNack(envelope.getDeliveryTag(), false, true); // Reintentar
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            this.getChannel().basicNack(envelope.getDeliveryTag(), false, true); // Reintentar
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        EntityManagerFactory entityManagerFactory = startEntityManagerFactory();
        TemperaturaWorker worker = new TemperaturaWorker(channel,queueName);
        worker.setEntityManagerFactory(entityManagerFactory);
        worker.init();
    }

}
