package org.example.study.repository;

import org.apache.ibatis.session.SqlSession;
import org.example.study.dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestDaoImpl implements TestDao{

  @Autowired
  private SqlSession session;

  @Override
  public void insert(TestDto dto) {
    session.insert("test.insert",dto);
  }

  @Override
  public List<TestDto> getList(TestDto dto) {
    List<TestDto> list=session.selectList("test.getList",dto);
    return list;
  }

  @Override
  public TestDto getDto(int num) {
    return session.selectOne("test.getDto",num);
  }

  @Override
  public void delete(int num) {
    session.delete("test.delete",num);
  }

  @Override
  public void update(TestDto dto) {
    session.update("test.update",dto);
  }

  @Override
  public int getCount(TestDto dto) {
    return session.selectOne("test.getCount", dto);
  }
}
