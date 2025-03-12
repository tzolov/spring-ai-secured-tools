package com.example.springaisecuredtools;

import org.slf4j.Logger;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class MyToolsSecured {
  private static final Logger log = org.slf4j.LoggerFactory.getLogger(MyToolsSecured.class);

  @PreAuthorize("hasRole('ROLE_USER')")
  public Weather getWeather(@ToolParam(description = "The zipcode") String zipcode) {
    log.info("Getting weather for " + zipcode);
    return new Weather("Sunny", "72");
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Secrets getSecretCode(@ToolParam(description = "The name of the secret code to get") String secret) {
    log.info("Getting secret code");
    return new Secrets("DontTellAnyone: " + secret);
  }
}
