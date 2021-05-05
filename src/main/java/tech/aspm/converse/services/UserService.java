package tech.aspm.converse.services;

import java.util.Properties;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private AmqpAdmin rabbitMQAdmin;
  @Autowired
  SimpleMessageListenerContainer container;
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
    container.addQueues(queue);
  }

  public void cleanup() {
    container.destroy();
    rabbitMQAdmin.deleteQueue(username);
  }
}
