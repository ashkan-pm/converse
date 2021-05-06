package tech.aspm.converse.services;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.aspm.converse.helpers.MessageQueueHelper;

@Service
public class UserService {
  private String username;
  private Queue queue;

  @Autowired
  private MessageQueueHelper queueHelper;

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

  public Queue login() {
    queue = queueHelper.declareQueue(username);
    return queue;
  }
}
