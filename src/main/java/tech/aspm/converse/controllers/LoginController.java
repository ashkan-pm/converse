package tech.aspm.converse.controllers;

import org.springframework.stereotype.Component;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("/views/login.fxml")
public class LoginController {
  private final FxWeaver fxWeaver;

  @FXML
  Stage stage;
  @FXML
  VBox loginBox;
  @FXML
  TextField usernameTxt;
  @FXML
  TextField channelTxt;
  @FXML
  CheckBox encChk;

  public LoginController(FxWeaver fxWeaver) {
    this.fxWeaver = fxWeaver;
  }

  public void handleLogin(Event event) {
    fxWeaver.loadController(ChatController.class).show();
  }
}
