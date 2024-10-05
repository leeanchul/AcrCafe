package org.example.acrcafe.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("userDto")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
  private String userName;
  private String id;
  private String password;
}