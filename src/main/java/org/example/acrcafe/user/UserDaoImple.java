package org.example.acrcafe.user;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImple implements UserDao{

  @Autowired
  private SqlSession session;

  @Override
  public UserDto getUser(String id) {
    UserDto result = session.selectOne("users.getUser", id);
    return result;
  }
}
