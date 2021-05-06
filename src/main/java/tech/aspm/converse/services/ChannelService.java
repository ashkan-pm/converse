package tech.aspm.converse.services;

import java.math.BigInteger;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.rgielen.fxweaver.core.FxWeaver;
import tech.aspm.converse.helpers.MessageQueueHelper;
import tech.aspm.converse.helpers.ClientQueueHelper;
import tech.aspm.converse.helpers.IssuerQueueHelper;
import tech.aspm.converse.models.PublicKeyWithParams;

@Service
public class ChannelService {
  private String name;
  private boolean isEncrypted;
  private String secret;

  @Autowired
  private SecretService secretService;
  @Autowired
  private UserService userService;
  @Autowired
  private ChannelService channelService;
  @Autowired
  private MessageQueueHelper messageQueueHelper;
  @Autowired
  private IssuerQueueHelper issuerQueueHelper;
  @Autowired
  private ClientQueueHelper clientQueueHelper;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getIsEncrypted() {
    return isEncrypted;
  }

  public void setIsEncrypted(boolean isEncrypted) {
    this.isEncrypted = isEncrypted;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public ChannelService(FxWeaver fxWeaver) {
  }

  public void join() {
    messageQueueHelper.declareBinding();

    issuerQueueHelper.declareQueue();
    clientQueueHelper.declareQueue();

    PublicKeyWithParams publicKeyWithParams = new PublicKeyWithParams(secretService.getGenerator(),
        secretService.getPrime(), channelService.getName(), userService.getUsername(), secretService.getPublicKey());
    clientQueueHelper.sendPublicKeyWithParams(publicKeyWithParams);
  }

  public void generateSecret() {
    Random randomGenerator = new Random();
    secret = new BigInteger(1024, randomGenerator).toString();
  }
}
