package org.example.study.repository;

import org.example.study.dto.GalleryDto;

import java.util.List;

public interface GalleryDao {
  void insert(GalleryDto dto);
  List<GalleryDto> getList();
}
