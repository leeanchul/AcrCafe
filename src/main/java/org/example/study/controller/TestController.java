package org.example.study.controller;

import org.apache.commons.io.IOUtils;
import org.example.study.dto.TestCommentDto;
import org.example.study.dto.TestDto;
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
    return "test/detail";
  }

  //업로드한 사진 보여주기위한 코드
  @ResponseBody
  @GetMapping(
      value = "/upload/{imageName}" ,
      // jpg, png, gif 이미지 데이터를 응답할수 있도록 produces 에 배열로 전달한다.
      produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE,
          MediaType.IMAGE_GIF_VALUE}
  )
  //@PathVariable 은Get방식에 imageName 을 String name으로 바인딩 하는것이다.
  public byte[] image(@PathVariable("imageName") String name) throws IOException {

    //읽어들일 파일의 절대 경로
    String absolutePath=fileLocation + File.separator + name;
    // 파일에서 읽어들일 InputStream
    InputStream is=new FileInputStream(absolutePath);
    // commons io 에 있는 IOUtils 클래스를 이용해서 이미지 파일에서 byte[] 을 얻어낸다
    return IOUtils.toByteArray(is);
  }
}
