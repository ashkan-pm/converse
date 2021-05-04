package tech.aspm.converse.controllers;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("/views/chat.fxml")
public class ChatController {
  private Stage stage;

  @FXML
  VBox chatBox;
  @FXML
  Label channelLbl;
  @FXML
  Button leaveBtn;
  @FXML
  TextArea chatTxt;
  @FXML
  TextField messageTxt;
  @FXML
  Button sendBtn;

  @FXML
  public void initialize() {
    this.stage = new Stage();
    stage.setScene(new Scene(chatBox));
  }

  public void show() {
    stage.show();
  }
}
