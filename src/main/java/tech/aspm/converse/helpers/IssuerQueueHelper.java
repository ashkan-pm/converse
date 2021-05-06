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

import tech.aspm.converse.models.PublicKey;
import tech.aspm.converse.models.Secret;
import tech.aspm.converse.services.ChannelService;
import tech.aspm.converse.services.RabbitService;

@Component
public class IssuerQueueHelper {
  @Value("${converse.rabbitmq.issuer.listenerId}")
  private String issuerListenerId;
  @Value("${converse.rabbitmq.issuer.queue}")
  private String issuerQueue;
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

  public void sendPublicKey(PublicKey message, String route) {
    rabbitMQService.send(route, message);
  }

  public void sendSecret(Secret message, String route) {
    rabbitMQService.send(route, message);
  }

  public Queue declareQueue() {
    String route = channelService.getName() + "." + issuerRoute;

    if (route.trim().equals("")) {
      throw new Error("Can not create a queue without a name");
    }

    Properties props = rabbitMQAdmin.getQueueProperties(issuerQueue);
    Queue queue = new Queue(issuerQueue);

    if (props == null) {
      rabbitMQAdmin.declareQueue(queue);
    }

    ((AbstractMessageListenerContainer) this.listenerEndpointRegistry.getListenerContainer(issuerListenerId))
        .addQueues(queue);
    Binding binding = BindingBuilder.bind(queue).to(exchange).with(route);
    rabbitMQAdmin.declareBinding(binding);

    return queue;
  }

  public void removeSession() {
    String route = channelService.getName() + "." + issuerRoute;
    Queue queue = new Queue(issuerQueue);

    ((AbstractMessageListenerContainer) this.listenerEndpointRegistry.getListenerContainer(issuerListenerId))
        .removeQueues(queue);
    Binding binding = BindingBuilder.bind(queue).to(exchange).with(route);
    rabbitMQAdmin.removeBinding(binding);
  }
}
