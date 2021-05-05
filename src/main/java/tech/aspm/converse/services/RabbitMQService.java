package tech.aspm.converse.services;

import com.mashape.unirest.http.Unirest;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.aspm.converse.RabbitMQConfig;

@Service
public class RabbitMQService {
  @Autowired
  private AmqpTemplate rabbitTemplate;

  public void send(String routingKey, String message) {
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, message);

    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Unirest.post("https://converse-7a5e.restdb.io/rest/message").header("content-type", "application/json")
              .header("x-apikey", "ba39539798330533464930b01b69d34f0e6fc").header("cache-control", "no-cache")
              .body("{\"username\":\"test\",\"body\":\"" + message + "\"}").asString();
        } catch (Exception e) {
          System.out.println("Couldn't write to database");
        }
      }
    }).start();
  }
}
