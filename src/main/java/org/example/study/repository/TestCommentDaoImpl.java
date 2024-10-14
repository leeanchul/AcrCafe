package org.example.study.repository;

import org.apache.ibatis.session.SqlSession;
import org.example.study.dto.TestCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestCommentDaoImpl implements TestCommentDao{

  @Autowired
  SqlSession session;

  @Override
  public List<TestCommentDto> getCommnetList(int ref_group) {
    List<TestCommentDto> list=session.selectList("testComment.getList",ref_group);
    return list;
  }

  @Override
  public void insert(TestCommentDto dto) {
    session.insert("testComment.insert",dto);
  }
}
