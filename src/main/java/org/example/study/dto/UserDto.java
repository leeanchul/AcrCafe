package org.example.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Alias("userDto")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
  private int num;
  private String nickName;
  private String userName;
  private String Password;
  private String Email;
  private String regdate;
  private String role;
  private String profile;
  private MultipartFile image;
  private String newPassword;
}
