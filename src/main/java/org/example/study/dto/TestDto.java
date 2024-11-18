package org.example.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("testDto")
@Builder
public class TestDto {
  private int num;
  private String writer;
  private String title;
  private String content;
  private String SaveFileName;
  private String regdate;
  private MultipartFile image;
  //페이징 처리를 위한 추가 필드
  private int startRowNum;
  private int endRowNum;
  //검색 기능 관련된 필드
  private String condition = "";
  private String keyword = "";
  private int pageNum = 1;
  //이전글과 다음글의 글번호를 담을 필드
  private int prevNum, nextNum;
}
