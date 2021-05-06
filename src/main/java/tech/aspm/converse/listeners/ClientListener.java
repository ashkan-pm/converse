
package tech.aspm.converse.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tech.aspm.converse.controllers.ChatController;
import tech.aspm.converse.models.PublicKey;
import tech.aspm.converse.models.Secret;
import tech.aspm.converse.services.ChannelService;
import tech.aspm.converse.services.SecretService;

@Component
public class ClientListener {
  @Autowired
  private SecretService secretService;
  @Autowired
  private ChannelService channelService;

  @RabbitListener(id = "${converse.rabbitmq.client.publicKeyListenerId}")
  public void publicKeyListenerId(PublicKey message) {
    secretService.calculateSecret(message.getPublicKey());
  }

  @RabbitListener(id = "${converse.rabbitmq.client.secretListenerId}")
  public void secretListenerId(Secret message) {
    if (channelService.getSecret() == null) {
      String secret = secretService.decrypt(message.getSecret());
      channelService.setSecret(secret);
    }
  }
}
