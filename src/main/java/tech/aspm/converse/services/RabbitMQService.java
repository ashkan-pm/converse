package tech.aspm.converse.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.aspm.converse.RabbitMQConfig;
import tech.aspm.converse.models.Message;

@Service
public class RabbitMQService {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void send(String routingKey, Message message) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      String orderJson = objectMapper.writeValueAsString(message);
      org.springframework.amqp.core.Message jsonMessage = org.springframework.amqp.core.MessageBuilder
          .withBody(orderJson.getBytes()).setContentType("application/json").build();
      this.rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, jsonMessage);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Unirest.post("https://converse-7a5e.restdb.io/rest/message").header("content-type", "application/json")
              .header("x-apikey", "ba39539798330533464930b01b69d34f0e6fc").header("cache-control", "no-cache")
              .body(message.toJSON()).asString();
        } catch (Exception e) {
          System.out.println("Couldn't write to database");
        }
      }
    }).start();
  }
}
