package org.example.study.repository;

import org.example.study.dto.GalleryDto;

import java.util.List;

public interface GalleryDao {
  public void insert(GalleryDto dto);
  public GalleryDto getData(int num);
  public int getCount();
  public List<GalleryDto> getList(GalleryDto dto);
  public void delete(int num);
}