package org.example.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Alias("galleryDto")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GalleryDto {
  private int num;
  private String writer;
  private String SaveFileName;
  private String regdate;
  private String caption;
  private MultipartFile image;
}
