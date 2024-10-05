package org.example.acrcafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
@PropertySource("classpath:application.properties")
@PropertySource(value = "classpath:custom.properties")
@SpringBootApplication
public class AcrCafeApplication {

  public static void main(String[] args) {
    SpringApplication.run(AcrCafeApplication.class, args);
  }

}
