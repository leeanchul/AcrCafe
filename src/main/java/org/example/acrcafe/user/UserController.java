package org.example.acrcafe.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @PostMapping("/api/test")
  public String test(){
    return "test";
  }
}
