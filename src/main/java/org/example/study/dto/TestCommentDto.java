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
  private String target_id;
  private int ref_group;
  private int comment_group;
  private String deleted;
  private String regdate;
  private String profile;
  //페이징 처리를 위한 필드
  private int startRowNum;
  private int endRowNum;
  private int pageNum;
}
