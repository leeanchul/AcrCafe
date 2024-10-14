package org.example.study.repository;

import org.apache.ibatis.session.SqlSession;
import org.example.study.dto.GalleryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GalleryDaoImpl implements GalleryDao{

  //핵심 의존 객체 DI
  @Autowired private SqlSession session;

  @Override
  public void insert(GalleryDto dto) {
    session.insert("gallery.insert", dto);
  }

  @Override
  public GalleryDto getData(int num) {
    return session.selectOne("gallery.getData", num);
  }

  @Override
  public int getCount() {
    return session.selectOne("gallery.getCount");
  }

  @Override
  public List<GalleryDto> getList(GalleryDto dto) {
    return session.selectList("gallery.getList", dto);
  }

  @Override
  public void delete(int num) {
    session.delete("gallery.delete", num);
  }

}