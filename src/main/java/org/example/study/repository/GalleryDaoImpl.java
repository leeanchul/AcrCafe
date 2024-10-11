package org.example.study.repository;

import org.apache.ibatis.session.SqlSession;
import org.example.study.dto.GalleryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GalleryDaoImpl implements GalleryDao{
  @Autowired
  private SqlSession session;

  @Override
  public void insert(GalleryDto dto) {
    session.insert("gallery.insert",dto);
  }

  @Override
  public List<GalleryDto> getList() {
    List<GalleryDto> list=session.selectList("gallery.getList");
    return list;
  }
}
