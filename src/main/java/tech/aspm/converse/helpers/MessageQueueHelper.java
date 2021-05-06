package tech.aspm.converse.helpers;

import java.util.Properties;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import tech.aspm.converse.models.Message;
import tech.aspm.converse.services.ChannelService;
import tech.aspm.converse.services.RabbitService;
import tech.aspm.converse.services.UserService;

@Component
public class MessageQueueHelper {
  @Value("${converse.rabbitmq.messageListenerId}")
  private String messageListenerId;

  @Autowired
  private RabbitListenerEndpointRegistry listenerEndpointRegistry;
  @Autowired
  RabbitService rabbitMQService;
  @Autowired
  private AmqpAdmin rabbitMQAdmin;
  @Autowired
  private TopicExchange exchange;
  @Autowired
  private UserService userService;
  @Autowired
  private ChannelService channelService;

  public void sendMessage(Message message) {
    rabbitMQService.send(channelService.getName(), message);
  }

  public Queue declareQueue(String name) {
    if (name.trim().equals("")) {
      throw new Error("Can not create a queue without a username");
    }

    Properties props = rabbitMQAdmin.getQueueProperties(name);
    Queue queue = new Queue(name);
    if (props == null) {
      rabbitMQAdmin.declareQueue(queue);
    }

    ((AbstractMessageListenerContainer) this.listenerEndpointRegistry.getListenerContainer(messageListenerId))
        .addQueues(queue);

    return queue;
  }

  public Binding declareBinding() {
    Queue queue = userService.getQueue();
    String channelName = channelService.getName().trim();

    if (channelName.equals("")) {
      throw new Error("Can not create a binding without a channel name");
    } else if (queue == null) {
      throw new Error("Can not create a binding without queue");
    }

    Binding binding = BindingBuilder.bind(queue).to(exchange).with(channelName);
    rabbitMQAdmin.declareBinding(binding);
    return binding;
  }

  public void removeSession() {
    Queue queue = userService.getQueue();
    String channelName = channelService.getName().trim();

    if (queue != null) {
      ((AbstractMessageListenerContainer) this.listenerEndpointRegistry.getListenerContainer(messageListenerId))
          .removeQueues(queue);
      rabbitMQAdmin.deleteQueue(queue.getName());
    }

    Binding binding = BindingBuilder.bind(queue).to(exchange).with(channelName);
    rabbitMQAdmin.removeBinding(binding);
  }
}
