package org.example.study.controller;

import org.apache.commons.io.IOUtils;
import org.example.study.dto.TestDto;
import org.example.study.dto.UserDto;
import org.example.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class UserController {
  // util 역활을 하는 서비스 객체를 인터페이스 type 으로 DI 받아서 사용한다
  @Autowired
  private UserService service;


  @PostMapping("/user/update")
  public String update(UserDto dto) {
    service.update(dto);
    return "redirect:/";
  }

  @GetMapping("/user/updateform")
  public String updateform(Model model) {
    service.getUser(model);
    return "user/updateform";
  }

  //회원 가입 요청처리
  @PostMapping("/user/signup")
  public String signup(UserDto dto) {
    //서비스(util) 객체를 이용해서 가입정보를 등록한다.
    service.addUser(dto);

    return "user/signup";
  }

  //회원가입폼 요청처리
  @GetMapping("/user/signupform")
  public String signupForm() {

    return "user/signupform";
  }

  //로그인이 필요한 요청경로를 로그인 하지 않은 상태로 요청하면 리다일렉트 되는 요청경로
  @GetMapping("/user/required_loginform")
  public String required_loginform() {
    return "user/required_loginform";
  }

  @RequestMapping("/user/loginform")
  public String loginform() {
    return "user/loginform";
  }
  //로그인 폼을 제출(post) 한 로그인 프로세즈 중에 forward 되는 경로이기 때문에 @PostMapping 임에 주의!

  @PostMapping("/user/login_fail")
  public String loginFail() {
    //로그인 실패임을 알릴 페이지
    return "user/login_fail";
  }

  @PostMapping("/user/login_success")
  public String loginSuccess() {
    return "user/login_success";
  }

}

