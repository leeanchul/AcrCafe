package org.example.study.service;

import org.example.study.dto.TestCommentDto;
import org.example.study.dto.TestDto;
import org.springframework.ui.Model;

public interface TestService {
  void insert(TestDto dto);
  void list(Model model, TestDto dto);
  void getDto(Model model, int num);

}
