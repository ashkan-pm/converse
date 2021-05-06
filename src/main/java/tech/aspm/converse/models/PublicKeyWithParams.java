package tech.aspm.converse.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Message.class)
public class PublicKeyWithParams extends PublicKey {
  private String generator;
  private String prime;
  private String channel;
  private String username;

  public String getGenerator() {
    return generator;
  }

  public void setGenerator(String generator) {
    this.generator = generator;
  }

  public String getPrime() {
    return prime;
  }

  public void setPrime(String prime) {
    this.prime = prime;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRoute() {
    return channel + "." + username;
  }

  public PublicKeyWithParams() {
  }

  public PublicKeyWithParams(String generator, String prime, String channel, String username, String publicKey) {
    this.generator = generator;
    this.prime = prime;
    this.channel = channel;
    this.username = username;
    setPublicKey(publicKey);
  }

  @Override
  public String toString() {
    return "PublicKeyWithParams [generator=" + generator + ", prime=" + prime + ", channel=" + channel + ", username="
        + username + ", publicKey=" + getPublicKey() + "]";
  }

  public String toJSON() {
    return "{\"generator\": \"" + generator + "\", \"prime\": \"" + prime + "\", \"channel\": \"" + channel
        + "\", \"username\": \"" + username + "\", \"publicKey\": \"" + getPublicKey() + "\"}";
  }
}
