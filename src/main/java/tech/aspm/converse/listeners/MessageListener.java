package tech.aspm.converse.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tech.aspm.converse.controllers.ChatController;
import tech.aspm.converse.models.Message;

@Component
public class MessageListener {
  @Autowired
  private ChatController chatController;

  @RabbitListener(id = "${converse.rabbitmq.messageListenerId}")
  public void receiveMessage(Message message) {
    chatController.pushMessage(message);
  }
}
