package org.example.study.service;

import org.example.study.dto.UserDto;
import org.springframework.ui.Model;

public interface UserService {
  void addUser(UserDto dto);
  void home(Model model);
  void getUser(Model model);
  void update(UserDto dto);
}
