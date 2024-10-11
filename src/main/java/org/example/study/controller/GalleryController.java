package org.example.study.controller;

import org.apache.commons.io.IOUtils;
import org.example.study.dto.GalleryDto;
import org.example.study.service.GalleryService;
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

@Controller
public class GalleryController {

  @Autowired
  private GalleryService service;
  @Value("${file.location}")
  private String fileLocation;

  @PostMapping("/gallery/upload")
  public String upload(GalleryDto dto){
    service.insert(dto);
    return "redirect:/gallery/list";
  }

  @GetMapping("/gallery/upload_form")
  public String uploadform(){
    return "gallery/upload_form";
  }
  @GetMapping("/gallery/list")
  public String list(Model model){
    service.list(model);

    return "gallery/list";
  }
  
//  //업로드한 사진 보여주기위한 코드
//  @ResponseBody
//  @GetMapping(
//      value = "/upload/{imageName}" ,
//      // jpg, png, gif 이미지 데이터를 응답할수 있도록 produces 에 배열로 전달한다.
//      produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE,
//          MediaType.IMAGE_GIF_VALUE}
//  )
//  //@PathVariable 은Get방식에 imageName 을 String name으로 바인딩 하는것이다.
//  public byte[] image(@PathVariable("imageName") String name) throws IOException {
//
//    //읽어들일 파일의 절대 경로
//    String absolutePath=fileLocation + File.separator + name;
//    // 파일에서 읽어들일 InputStream
//    InputStream is=new FileInputStream(absolutePath);
//    // commons io 에 있는 IOUtils 클래스를 이용해서 이미지 파일에서 byte[] 을 얻어낸다
//    return IOUtils.toByteArray(is);
//  }
}
