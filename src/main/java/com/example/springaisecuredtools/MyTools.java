package com.example.springaisecuredtools;

import org.slf4j.Logger;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class MyTools {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(MyTools.class);
  private final MyToolsSecured myToolsSecured;

  public MyTools(MyToolsSecured myToolsSecured) {
    this.myToolsSecured = myToolsSecured;
  }

  @Tool(name = "getWeather", description = "Gets the weather conditions for a given zipcode")
  public Weather getWeather(@ToolParam(description = "The zipcode") String zipcode) {
    log.info("Getting weather for " + zipcode);
    return myToolsSecured.getWeather(zipcode);
  }

  @Tool(name = "getSecretCode", description = "Gets the secret code")
  public Secrets getSecretCode(@ToolParam(description = "The name of the secret code to get") String secret) {
    log.info("Getting secret code");
    return myToolsSecured.getSecretCode(secret);
  }

}
