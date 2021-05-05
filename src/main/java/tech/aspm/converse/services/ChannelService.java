package tech.aspm.converse.services;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.rgielen.fxweaver.core.FxWeaver;
import tech.aspm.converse.controllers.ChatController;
import tech.aspm.converse.models.Message;

@Service
public class ChannelService {
  @Autowired
  private ChatController chatController;
  @Autowired
  private UserService userService;
  @Autowired
  RabbitMQService rabbitMQService;
  @Autowired
  private AmqpAdmin rabbitMQAdmin;
  @Autowired
  private TopicExchange exchange;
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

  @RabbitListener(id = "message")
  public void receiveMessage(Message message) {
    chatController.pushMessage(message);
  }

  public void sendMessage(Message message) {
    rabbitMQService.send(name, message);
  }

  public void createBinding() {
    if (name.equals("")) {
      throw new Error("Can not create a binding without a channel name");
    } else if (userService.getQueue() == null) {
      throw new Error("Can not create a binding without queue");
    }

    Queue queue = userService.getQueue();
    Binding binding = BindingBuilder.bind(queue).to(exchange).with(name);
    rabbitMQAdmin.declareBinding(binding);
  }

  public void cleanup() {
    Queue queue = userService.getQueue();
    Binding binding = BindingBuilder.bind(queue).to(exchange).with(name);
    rabbitMQAdmin.removeBinding(binding);
  }
}
