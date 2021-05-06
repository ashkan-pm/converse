package tech.aspm.converse.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tech.aspm.converse.interfaces.QueueMessage;

@Service
public class RabbitService {
  @Value("${converse.rabbitmq.exchangeName}")
  String exchangeName;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void send(String routingKey, QueueMessage message) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      String orderJson = objectMapper.writeValueAsString(message);
      org.springframework.amqp.core.Message jsonMessage = org.springframework.amqp.core.MessageBuilder
          .withBody(orderJson.getBytes()).setContentType("application/json").build();
      this.rabbitTemplate.convertAndSend(exchangeName, routingKey, jsonMessage);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
