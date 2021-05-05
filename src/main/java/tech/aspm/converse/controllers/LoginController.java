package tech.aspm.converse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
  VBox loginBox;
  @FXML
  TextField usernameTxt;
  @FXML
  TextField channelTxt;
  @FXML
  CheckBox encChk;
  @FXML
  Button loginBtn;

  @FXML
  public void initialize() {
    usernameTxt.setText(userService.getUsername());
    channelTxt.setText(channelService.getName());
  }

  public LoginController(FxWeaver fxWeaver) {
    this.fxWeaver = fxWeaver;
  }

  public void loginMouseEntered(MouseEvent event) {
    loginBtn.setStyle(loginBtn.getStyle() + "; -fx-border-color: #f5a055;");
  }

  public void loginMouseExited(MouseEvent event) {
    loginBtn.setStyle(loginBtn.getStyle() + "; -fx-border-color: #f8f8f8;");
  }

  public void loginMousePressed(MouseEvent event) {
    loginBtn.setStyle(loginBtn.getStyle() + "; -fx-background-color: #d8d8d8;");
  }

  public void loginMouseReleased(MouseEvent event) {
    loginBtn.setStyle(loginBtn.getStyle() + "; -fx-background-color: #f8f8f8;");
  }

  public void handleLogin(ActionEvent event) {
    userService.setUsername(usernameTxt.getText());
    userService.createQueue();
    channelService.setName(channelTxt.getText());
    channelService.setSecure(encChk.isSelected());
    channelService.createBinding();

    Stage stage = (Stage) loginBox.getScene().getWindow();
    Scene scene = new Scene(fxWeaver.loadView(ChatController.class));
    stage.setScene(scene);
  }
}
