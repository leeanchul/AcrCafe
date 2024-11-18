package org.example.study.repository;

import org.example.study.dto.CafeDto;
import org.example.study.dto.TestDto;

import java.util.List;

public interface CafeDao {
  void insert(CafeDto dto);

  List<CafeDto> getList(CafeDto dto);

  void delete(int num);

  void update(CafeDto dto);

  CafeDto getDto(int num);
}
