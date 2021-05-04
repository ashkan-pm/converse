package tech.aspm.converse.services;

import org.springframework.stereotype.Service;

@Service
public class ChannelService {
  private String name;
  private boolean secure;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getSecure() {
    return secure;
  }

  public void setSecure(boolean secure) {
    this.secure = secure;
  }

  public ChannelService(String name) {
    this.name = name;
  }

  public ChannelService() {
  }
}
