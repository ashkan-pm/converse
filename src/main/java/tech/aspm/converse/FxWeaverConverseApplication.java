package tech.aspm.converse;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;
import tech.aspm.converse.services.ChannelService;
import tech.aspm.converse.services.UserService;

@SpringBootApplication
public class FxWeaverConverseApplication {
  @Autowired
  private UserService userService;
  @Autowired
  private ChannelService channelService;

  public static void main(String[] args) {
    Application.launch(ConverseApplication.class, args);
  }

  @PreDestroy
  private void cleanup() {
    channelService.cleanup();
    userService.cleanup();
  }
}
