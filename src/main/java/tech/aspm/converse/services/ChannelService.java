package tech.aspm.converse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.rgielen.fxweaver.core.FxWeaver;
import tech.aspm.converse.helpers.MessageQueueHelper;

@Service
public class ChannelService {
  private String name;
  private boolean isEncrypted;

  @Autowired
  private MessageQueueHelper messageQueueHelper;

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

  public ChannelService(FxWeaver fxWeaver) {
  }

  public void join() {
    messageQueueHelper.declareBinding();
  }
}
