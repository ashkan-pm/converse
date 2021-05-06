
package tech.aspm.converse.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tech.aspm.converse.helpers.IssuerQueueHelper;
import tech.aspm.converse.models.PublicKey;
import tech.aspm.converse.models.PublicKeyWithParams;
import tech.aspm.converse.models.Secret;
import tech.aspm.converse.services.ChannelService;
import tech.aspm.converse.services.SecretService;
import tech.aspm.converse.services.UserService;

@Component
public class IssuerListener {
  @Autowired
  private IssuerQueueHelper issuerQueueHelper;
  @Autowired
  private SecretService secretService;
  @Autowired
  private ChannelService channelService;
  @Autowired
  private UserService userervice;

  @RabbitListener(id = "${converse.rabbitmq.issuer.listenerId}")
  public void issuerListenerId(PublicKeyWithParams message) {
    try {
      String username = message.getUsername();

      if (!userervice.getUsername().equals(username)) {
        secretService.setGenerator(message.getGenerator());
        secretService.setPrime(message.getPrime());
        secretService.generatePublicKey();
        secretService.calculateSecret(message.getPublicKey());
        PublicKey publicKey = new PublicKey(secretService.getPublicKey());
        issuerQueueHelper.sendPublicKey(publicKey, message.getRoute());

        if (channelService.getSecret() == null) {
          channelService.generateSecret();
        }

        Secret secret = new Secret(secretService.encrypt(channelService.getSecret()));
        issuerQueueHelper.sendSecret(secret, message.getRoute());
      }
    } catch (Exception e) {
      System.out.println("Could not handle secret request");
    }
  }
}
