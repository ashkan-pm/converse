package tech.aspm.converse.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import tech.aspm.converse.interfaces.QueueMessage;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Message.class)
public class PublicKey implements QueueMessage {
  private String publicKey;

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  public PublicKey() {
  }

  public PublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  @Override
  public String toString() {
    return "PublicKey [publicKey=" + publicKey + "]";
  }

  public String toJSON() {
    return "{\"publicKey\": \"" + publicKey + "\"}";
  }
}
