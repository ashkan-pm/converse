package tech.aspm.converse.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Message.class)
public class Message {
  private String username;
  private String body;
  private String channel;
  private Date createdAt;
  private boolean isEncrypted;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public boolean getIsEncrypted() {
    return isEncrypted;
  }

  public void setIsEncrypted(boolean isEncrypted) {
    this.isEncrypted = isEncrypted;
  }

  @Override
  public String toString() {
    return "Message [username=" + username + ", body=" + body + ", channel=" + channel + ", createdAt=" + createdAt
        + ", isEncrypted=" + isEncrypted + "]";
  }

  public String toJSON() {
    return "{\"username\": \"" + username + "\", \"body\": \"" + body + "\", \"channel\": \"" + channel
        + "\", \"createdAt\": \"" + createdAt + "\", \"isEncrypted\": \"" + isEncrypted + "\"}";
  }
}
