package tech.aspm.converse.services;

import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Value;

import tech.aspm.converse.interfaces.QueueMessage;

public class RestService {
  @Value("${converse.rest.apiUrl}")
  private String apiUrl;
  @Value("${converse.rest.apiKey}")
  private String apiKey;

  public void post(String resource, QueueMessage message) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Unirest.post(apiUrl + resource).header("content-type", "application/json").header("x-apikey", apiKey)
              .header("cache-control", "no-cache").body(message.toJSON()).asString();
        } catch (Exception e) {
          System.out.println("Could not write to database");
        }
      }
    }).start();
  }
}
