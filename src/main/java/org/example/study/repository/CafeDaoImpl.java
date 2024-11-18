package org.example.study.repository;

import org.apache.ibatis.session.SqlSession;
import org.example.study.dto.CafeDto;
import org.example.study.dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CafeDaoImpl implements CafeDao {
  @Autowired
  private SqlSession session;

  @Override
  public void insert(CafeDto dto) {
    session.insert("cafe.insert", dto);
  }

  @Override
  public List<CafeDto> getList(CafeDto dto) {
    List<CafeDto> list = session.selectList("cafe.getList", dto);
    return list;
  }

  @Override
  public void delete(int num) {
    session.delete("cafe.delete", num);
  }

  @Override
  public void update(CafeDto dto) {
    session.update("cafe.update", dto);
  }

  @Override
  public CafeDto getDto(int num) {
    return session.selectOne("cafe.getDto", num);
  }
}
