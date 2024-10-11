package org.example.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

@SpringBootApplication
//업로드도니느 이미지 저장하는 위치정보 로딩
@PropertySource(value = "classpath:custom.properties")
public class StudyApplication {

  public static void main(String[] args) {
    SpringApplication.run(StudyApplication.class, args);
  }

}
