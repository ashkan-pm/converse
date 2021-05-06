package tech.aspm.converse;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;
import tech.aspm.converse.fx.FXWeaverConverseApplication;
import tech.aspm.converse.helpers.ClientQueueHelper;
import tech.aspm.converse.helpers.IssuerQueueHelper;
import tech.aspm.converse.helpers.MessageQueueHelper;

@SpringBootApplication
public class ConverseApplication {
  @Autowired
  private MessageQueueHelper messageQueueHelper;
  @Autowired
  private ClientQueueHelper clientQueueHelper;
  @Autowired
  private IssuerQueueHelper issuerQueueHelper;

  public static void main(String[] args) {
    Application.launch(FXWeaverConverseApplication.class, args);
  }

  @PreDestroy
  private void cleanup() {
    messageQueueHelper.removeSession();
    clientQueueHelper.removeSession();
    issuerQueueHelper.removeSession();
  }
}
