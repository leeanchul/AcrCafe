package org.example.study.controller;

import org.apache.commons.io.IOUtils;
import org.example.study.dto.GalleryDto;
import org.example.study.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class GalleryController {

  @Autowired
  private GalleryService service;
  @Value("${file.location}")
  private String fileLocation;

  @GetMapping("/gallery/delete")
  public String delete(int num) {
    service.deleteOne(num);
    return "redirect:/gallery/list";
  }

  @GetMapping("/gallery/detail")
  public String detail(int num, Model model) {
    service.selectOne(model, num);
    return "gallery/detail";
  }

  //전달되는 pageNum 이 있는지 확인해서 있으면 추출하고 없으면 기본값 1 로 설정
  @GetMapping("/gallery/list")
  public String list(@RequestParam(defaultValue = "1") int pageNum, Model model) {
    /*
     *  서비스에 Model 객체와 pageNum 을 전달해서
     *  Model 에 pageNum 에 해당하는 글 목록이 담기도록 한다.
     *  Model 에 담긴 내용을 view page(Thymeleaf 템플릿페이지) 에서 사용할수 있다
     */
    service.selectPage(model, pageNum);
    return "gallery/list";
  }

  @GetMapping("/gallery/upload_form")
  public String uploadForm() {
    return "gallery/upload_form";
  }

  @PostMapping("/gallery/upload")
  public String upload(GalleryDto dto) {
    //caption 과 image 가 들어 있는 GalleryDto 를 서비스에 전달해서 저장한다
    service.addToGallery(dto);

    //목록보기로 임시 리다일렉트 이동 시키기
    return "redirect:/gallery/list";
  }
}
