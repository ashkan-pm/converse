package tech.aspm.converse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import tech.aspm.converse.services.ChannelService;
import tech.aspm.converse.services.UserService;

@Component
@FxmlView("/views/login.fxml")
public class LoginController {
  @Autowired
  private UserService userService;
  @Autowired
  private ChannelService channelService;
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
    userService.setUsername(usernameTxt.getText());
    channelService.setName(channelTxt.getText());
    channelService.setSecure(encChk.isSelected());
    fxWeaver.loadController(ChatController.class).show();
  }
}
