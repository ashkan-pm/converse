package tech.aspm.converse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import tech.aspm.converse.services.ChannelService;
import tech.aspm.converse.services.UserService;

@Component
@FxmlView("/views/chat.fxml")
public class ChatController {
  @Autowired
  private UserService userService;
  @Autowired
  private ChannelService channelService;
  private Stage stage;

  @FXML
  VBox chatBox;
  @FXML
  ImageView userImg;
  @FXML
  Label userLbl;
  @FXML
  ImageView channelImg;
  @FXML
  Label channelLbl;
  @FXML
  ImageView unlockImg;
  @FXML
  Label unlockLbl;
  @FXML
  ImageView lockImg;
  @FXML
  Label lockLbl;
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
    userLbl.setText(userService.getUsername());
    channelLbl.setText(channelService.getName());

    boolean secure = channelService.getSecure();
    unlockImg.setVisible(!secure);
    unlockLbl.setVisible(!secure);
    lockImg.setVisible(secure);
    lockLbl.setVisible(secure);

    stage.show();
  }

  public void handleLeave(Event event) {
    stage.close();
  }
}
