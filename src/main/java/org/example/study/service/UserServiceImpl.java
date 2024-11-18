package org.example.study.service;

import org.example.study.dto.TestDto;
import org.example.study.dto.UserDto;
import org.example.study.repository.TestDao;
import org.example.study.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Security;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
  @Autowired
  private UserDao dao;
  //비밀번호 암호화하는 객체도 bean 으로 등록이 되어 있다.
  @Autowired
  private PasswordEncoder encoder;
  //업로드된 이미지를 저장할 파일시스템 상의 위치
  @Value("${file.location}")
  private String fileLocation;
  @Override
  public void addUser(UserDto dto) {
    //암호화된 비밀 번호를 얻어내서
    String encodedPwd=encoder.encode(dto.getPassword());
    //dto 에 덮어쓰기 한다음
    dto.setPassword(encodedPwd);
    //일반 사용자라는 의미에서 role 에 "USER" 를 넣어준다.
    dto.setRole("USER");
    //DB 에 저장한다.
    dao.insert(dto);
  }

  @Override
  public void home(Model model) {
    //spring securty에서 현재 사용자의 인증 정보를 가져오는 것이다.
    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
    if(authentication !=null&& !"anonymousUser".equals(authentication.getName())){
      String userName=authentication.getName();
      model.addAttribute("userName",userName);
      UserDto dto=dao.getUser(userName);
      model.addAttribute("dto",dto);
    } else {
      model.addAttribute("userName", null);
    }
  }

  @Override
  public void getUser(Model model) {
    String userName= SecurityContextHolder.getContext().getAuthentication().getName();
    UserDto dto=dao.getUser(userName);
    model.addAttribute("dto",dto);
  }

  //사용장 이미지 업데이트 추가하기
  @Override
  public void update(UserDto dto) {
    MultipartFile image=dto.getImage();
    //만일 선택한 프로필 이미지가 있다면
    if(image.getSize() != 0) {
      //저장할 파일의 이름 겹치지 않는 유일한 문자열로 얻어내기
      String saveFileName= UUID.randomUUID().toString();
      //저장할 파일의 전체 경로 구성하기
      String filePath=fileLocation+ File.separator+saveFileName;
      try {
        //업로드된 파일을 이동시킬 목적지 File 객체
        File f=new File(filePath);
        //MultipartFile 객체의 메소드를 통해서 실제로 이동시키기(전송하기)
        dto.getImage().transferTo(f);
      }catch(Exception e) {
        e.printStackTrace();
      }
      //UserDto 에 저장된 이미지의 이름을 넣어준다.
      dto.setProfile(saveFileName);
    }
    //로그인된 userName 도 dto 에 담아준다
    String userName=SecurityContextHolder.getContext().getAuthentication().getName();
    dto.setUserName(userName);

    //dao 를 이용해서 수정반영한다
    dao.update(dto);
  }


}
