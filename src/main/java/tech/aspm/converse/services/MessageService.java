package tech.aspm.converse.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tech.aspm.converse.interfaces.QueueMessage;

@Service
public class MessageService extends RestService {
  @Value("${converse.rest.messageResource}")
  private String messageResource;

  public void saveMessage(QueueMessage message) {
    post(messageResource, message);
  }
}
