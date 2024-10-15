package org.example.study.controller;

import org.apache.commons.io.IOUtils;
import org.example.study.dto.TestCommentDto;
import org.example.study.dto.TestDto;
import org.example.study.dto.UserDto;
import org.example.study.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

  @Autowired
  private TestService service;

  @Value("${file.location}")
  private String fileLocation;

  @PostMapping("/test/insertComment")
  public String insert(TestCommentDto dto){
    service.commentInsert(dto);
    //댓글 입력하고,현재창에 있게하기위해서
    return "redirect:/test/detail?num=" + dto.getRef_group();
  }

  @GetMapping("/test/list")
  public String list(Model model,TestDto dto){
   service.list(model,dto);
    return "test/list";
  }
  @GetMapping("/test/insertform")
  public String insertform(){
    return "test/insertform";
  }

  @PostMapping("/test/insert")
  public String insert(TestDto dto){
    System.out.println(dto.getImage());
    service.insert(dto);
    return "redirect:/test/list";
  }

  @GetMapping("/test/detail")
  public String detail(Model model,int num){
    service.getDto(model,num);
    service.commentList(model,num);
    return "test/detail";
  }

  @GetMapping("/test/delete")
  public String delete(int num){
    service.delete(num);
    return "redirect:/test/list";
  }
  @GetMapping("/test/updateform")
  public String updateform(Model model,int num){
    service.getDto(model,num);
    return "test/updateform";
  }

  @PostMapping("/test/update")
  public String update(TestDto dto){
    System.out.println(dto);
    service.update(dto);
    return "redirect:/test/list";
  }

}
