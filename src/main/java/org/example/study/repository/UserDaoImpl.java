package org.example.study.repository;

import org.apache.ibatis.session.SqlSession;
import org.example.study.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
  @Autowired
  SqlSession session;

  @Override
  public void insert(UserDto dto) {
    session.insert("user.insert", dto);
  }

  @Override
  public UserDto getUser(String userName) {
    return session.selectOne("user.getUser", userName);
  }

  @Override
  public void update(UserDto dto) {
    session.update("user.update", dto);
  }
}
