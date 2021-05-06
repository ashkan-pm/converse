package tech.aspm.converse;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;
import tech.aspm.converse.fx.FXWeaverConverseApplication;
import tech.aspm.converse.helpers.MessageQueueHelper;

@SpringBootApplication
public class ConverseApplication {
  @Autowired
  private MessageQueueHelper messageQueueHelper;

  public static void main(String[] args) {
    Application.launch(FXWeaverConverseApplication.class, args);
  }

  @PreDestroy
  private void cleanup() {
    messageQueueHelper.removeSession();
  }
}
