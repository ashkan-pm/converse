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

import tech.aspm.converse.models.PublicKeyWithParams;
import tech.aspm.converse.services.ChannelService;
import tech.aspm.converse.services.RabbitService;
import tech.aspm.converse.services.UserService;

@Component
public class ClientQueueHelper {
  @Value("${converse.rabbitmq.client.publicKeyListenerId}")
  private String publicKeyListenerId;
  @Value("${converse.rabbitmq.client.secretListenerId}")
  private String secretListenerId;
  @Value("${converse.rabbitmq.issuer.route}")
  private String issuerRoute;

  @Autowired
  private RabbitListenerEndpointRegistry listenerEndpointRegistry;
  @Autowired
  RabbitService rabbitMQService;
  @Autowired
  private AmqpAdmin rabbitMQAdmin;
  @Autowired
  private TopicExchange exchange;
  @Autowired
  private ChannelService channelService;
  @Autowired
  private UserService userService;

  public void sendPublicKeyWithParams(PublicKeyWithParams message) {
    String fullRoute = channelService.getName() + "." + issuerRoute;
    rabbitMQService.send(fullRoute, message);
  }

  public Queue declareQueue() {
    String route = channelService.getName() + "." + userService.getUsername();

    if (route.trim().equals("")) {
      throw new Error("Can not create a queue without a name");
    }

    Properties props = rabbitMQAdmin.getQueueProperties(route);
    Queue queue = new Queue(route);

    if (props == null) {
      rabbitMQAdmin.declareQueue(queue);
    }

    ((AbstractMessageListenerContainer) this.listenerEndpointRegistry.getListenerContainer(publicKeyListenerId))
        .addQueues(queue);
    ((AbstractMessageListenerContainer) this.listenerEndpointRegistry.getListenerContainer(secretListenerId))
        .addQueues(queue);
    Binding binding = BindingBuilder.bind(queue).to(exchange).with(route);
    rabbitMQAdmin.declareBinding(binding);

    return queue;
  }

  public void removeSession() {
    String route = channelService.getName() + "." + userService.getUsername();
    Queue queue = new Queue(route);

    rabbitMQAdmin.deleteQueue(queue.getName());
    ((AbstractMessageListenerContainer) this.listenerEndpointRegistry.getListenerContainer(publicKeyListenerId))
        .removeQueues(queue);
    ((AbstractMessageListenerContainer) this.listenerEndpointRegistry.getListenerContainer(secretListenerId))
        .removeQueues(queue);
    Binding binding = BindingBuilder.bind(queue).to(exchange).with(route);
    rabbitMQAdmin.removeBinding(binding);
  }
}
