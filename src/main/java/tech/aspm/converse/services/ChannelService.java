package tech.aspm.converse.services;

import java.util.Properties;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.rgielen.fxweaver.core.FxWeaver;
import tech.aspm.converse.controllers.ChatController;

@Service
public class ChannelService {
  @Autowired
  private ChatController chatController;
  @Autowired
  RabbitMQService rabbitMQService;
  @Autowired
  private AmqpAdmin rabbitMQAdmin;
  @Autowired
  private TopicExchange exchange;
  @Autowired
  SimpleMessageListenerContainer container;
  private String name;
  private boolean secure;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getSecure() {
    return secure;
  }

  public void setSecure(boolean secure) {
    this.secure = secure;
  }

  public ChannelService(FxWeaver fxWeaver) {
  }

  public void receiveMessage(String message) {
    chatController.pushMessage(message);
  }

  public void sendMessage(String message) {
    rabbitMQService.send(name, message);
  }

  public void createBinding(Queue queue) {
    if (name.equals("")) {
      throw new Error("Can not create a binding without a channel name");
    }

    Binding binding = BindingBuilder.bind(queue).to(exchange).with(name);
    rabbitMQAdmin.declareBinding(binding);
    container.addQueues(queue);
  }
}
