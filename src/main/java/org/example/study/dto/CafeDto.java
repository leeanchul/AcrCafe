package org.example.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("cafeDto")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CafeDto {
  private int num;
  private String title;
  private String content;
  private String writer;
  private String regdate;
}
