package tech.aspm.converse.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tech.aspm.converse.controllers.ChatController;
import tech.aspm.converse.helpers.CryptoHelper;
import tech.aspm.converse.models.Message;
import tech.aspm.converse.services.ChannelService;

@Component
public class MessageListener {
  @Autowired
  private ChatController chatController;
  @Autowired
  private ChannelService channelService;

  @RabbitListener(id = "${converse.rabbitmq.message.listenerId}")
  public void receiveMessage(Message message) {
    if (message.getIsEncrypted()) {
      message.setBody(CryptoHelper.decrypt(message.getBody(), channelService.getSecret()));
    }

    chatController.pushMessage(message);
  }
}