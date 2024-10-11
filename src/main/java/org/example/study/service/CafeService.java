package org.example.study.service;

import org.example.study.dto.CafeDto;
import org.springframework.ui.Model;

public interface CafeService {
  void insert(CafeDto dto);
  void getList(Model model, CafeDto dto);
  void delete(int num);

  void update(CafeDto dto);
  void getDto(Model model, int num);

}
