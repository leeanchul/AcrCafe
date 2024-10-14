package org.example.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("testCommentDto")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestCommentDto {
  private int num;
  private String writer;
  private String content;
  private int ref_group;
  private String regdate;
}
