package org.example.study.controller;

import org.apache.ibatis.annotations.Param;
import org.example.study.dto.CafeDto;
import org.example.study.service.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Parameter;

@Controller
public class CafeController {
@Autowired
  private CafeService service;

@PostMapping("/cafe/update")
public String update(CafeDto dto){
  service.update(dto);
  return "redirect:/cafe/list";
}

@GetMapping("/cafe/updateform")
public String updateform(Model model,int num){
  service.getDto(model,num);
  return "/cafe/updateform";
}

@GetMapping("/cafe/delete")
public String delete(int num){
  service.delete(num);
  return "redirect:/cafe/list";
}
  @PostMapping("/cafe/add")
  public String add(CafeDto dto){
    service.insert(dto);
    return "redirect:/cafe/list";
  }

  @GetMapping("/cafe/list")
  public String list(Model model,CafeDto dto){
    service.getList(model, dto);
    return "cafe/list";
  }
  @GetMapping("/cafe/addform")
  public String addform(){
    return "cafe/addform";
  }
}
