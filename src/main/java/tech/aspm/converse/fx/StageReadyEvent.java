package tech.aspm.converse.fx;

import org.springframework.context.ApplicationEvent;

import javafx.stage.Stage;

public class StageReadyEvent extends ApplicationEvent {
  public final Stage stage;

  public StageReadyEvent(Stage stage) {
    super(stage);
    this.stage = stage;
  }
}