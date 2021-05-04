package tech.aspm.converse.services;

import java.util.Properties;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private AmqpAdmin rabbitMQAdmin;
  private String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserService() {
  }

  public Queue createQueue() {
    if (username.equals("")) {
      throw new Error("Can not create a queue without a username");
    }

    Properties props = rabbitMQAdmin.getQueueProperties(username);
    if (props == null) {
      Queue queue = new Queue(username);
      rabbitMQAdmin.declareQueue(queue);
      return queue;
    } else {
      Queue queue = new Queue(username);
      return queue;
    }
  }
}
