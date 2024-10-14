package org.example.study.repository;

import org.example.study.dto.TestCommentDto;

import java.util.List;

public interface TestCommentDao {
  List<TestCommentDto> getCommnetList(int ref_group);
  void insert(TestCommentDto dto);
}
