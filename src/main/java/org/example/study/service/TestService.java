package org.example.study.service;

import org.example.study.dto.CafeDto;
import org.example.study.dto.TestCommentDto;
import org.example.study.dto.TestDto;
import org.springframework.ui.Model;

public interface TestService {
  void insert(TestDto dto);
  void list(Model model, TestDto dto);
  void getDto(Model model, int num);
  void delete(int num);
  void update(TestDto dto);

  //댓글
  void commentList(Model model,int ref_group);
  void commentInsert(TestCommentDto dto);
}