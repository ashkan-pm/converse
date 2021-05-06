package tech.aspm.converse.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import tech.aspm.converse.interfaces.QueueMessage;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Message.class)
public class Secret implements QueueMessage {
  private String secret;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public Secret() {
  }

  public Secret(String secret) {
    this.secret = secret;
  }

  @Override
  public String toString() {
    return "Secret [secret=" + secret + "]";
  }

  public String toJSON() {
    return "{\"secret\": \"" + secret + "\"}";
  }
}
