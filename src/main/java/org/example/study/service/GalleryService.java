package org.example.study.service;

import org.example.study.dto.GalleryDto;
import org.springframework.ui.Model;

public interface GalleryService {
  void insert(GalleryDto dto);
  void list(Model model);
}
