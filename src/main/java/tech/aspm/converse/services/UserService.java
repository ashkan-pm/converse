package tech.aspm.converse.services;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  private String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserService(String username) {
    this.username = username;
  }

  public UserService() {
  }
}
