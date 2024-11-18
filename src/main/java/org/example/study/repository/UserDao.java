package org.example.study.repository;

import org.example.study.dto.UserDto;

public interface UserDao {
  void insert(UserDto dto);

  UserDto getUser(String userName);

  void update(UserDto dto);

}
