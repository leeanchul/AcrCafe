package org.example.study.repository;

import org.example.study.dto.CafeDto;
import org.example.study.dto.TestDto;

import java.util.List;

public interface TestDao {
  void insert(TestDto dto);
  List<TestDto> getList(TestDto dto);
  TestDto getDto(int num);

  int getCount(TestDto dto);
}
