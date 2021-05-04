package tech.aspm.converse.services;

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
  }
}
