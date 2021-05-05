package tech.aspm.converse.services;

import java.util.Properties;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private AmqpAdmin rabbitMQAdmin;
  @Autowired
  private RabbitListenerEndpointRegistry listenerEndpointRegistry;
  private String username;
  private Queue queue;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Queue getQueue() {
    return queue;
  }

  public UserService() {
  }

  public void createQueue() {
    if (username.equals("")) {
      throw new Error("Can not create a queue without a username");
    }

    Properties props = rabbitMQAdmin.getQueueProperties(username);
    Queue queue = new Queue(username);
    if (props == null) {
      rabbitMQAdmin.declareQueue(queue);
    }

    this.queue = queue;
    ((AbstractMessageListenerContainer) this.listenerEndpointRegistry.getListenerContainer("message")).addQueues(queue);
  }

  public void cleanup() {
    ((AbstractMessageListenerContainer) this.listenerEndpointRegistry.getListenerContainer("message")).removeQueues(queue);
    rabbitMQAdmin.deleteQueue(username);
  }
}
