package org.example.study.controller;

import org.example.study.config.SecurityConfig;
import org.example.study.dto.TestDto;
import org.example.study.service.TestService;
import org.example.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {


  @Autowired
  private UserService service;
  @GetMapping("/")
  public String home(Model model){
    //서비스에서 해야
    service.home(model);
    return "home";
  }

  @GetMapping("/play")
  @ResponseBody
  public String play(){
    return "놀기!!";
  }

  @GetMapping("/notice")
  public String notice(Model model){
    List<String> list=new ArrayList<>();
    list.add("이종욱(CF)");
    list.add("고영민(2B)");
    list.add("김현수(LF)");
    list.add("김동주(1B)");
    list.add("양의지(C)");
    list.add("허경민(3B)");
    list.add("에반스(DH)");
    list.add("손시헌(SS)");
    list.add("정수빈(RF)");
    list.add("니퍼트(P)");
    String team="두산 라인업";
    model.addAttribute("team",team);
    model.addAttribute("list",list);
    return "notice";
  }
  @ResponseBody
  @GetMapping("/security")
  public String security(){
    return "비밀인데 ㅋ";
  }
}
