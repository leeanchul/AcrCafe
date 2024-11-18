package org.example.study.service;

import org.example.study.dto.GalleryDto;
import org.springframework.ui.Model;

public interface GalleryService {
  public void addToGallery(GalleryDto dto);

  public void selectOne(Model model, int num);

  public void selectPage(Model model, int pageNum);

  public void deleteOne(int num);
}
