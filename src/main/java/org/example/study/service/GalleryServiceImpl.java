package org.example.study.service;

import org.example.study.dto.GalleryDto;
import org.example.study.repository.GalleryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class GalleryServiceImpl implements GalleryService{
  @Autowired
  private GalleryDao dao;

  //파일을 저장할 위치
  @Value("${file.location}")
  private String fileLocation;

  @Override
  public void insert(GalleryDto dto) {
    //1.업로도된 파일 저장
    //저장할 파일의 이름 겹치지 않는 유일한 문자열로 얻어내기
    String saveFileName= UUID.randomUUID().toString();
    //저장할 파일의 전체 경로 구성하기
    String filePath=fileLocation+ File.separator+saveFileName;
    try{
      //업로드된 파일을 이동시킬 목저지 파일 객체
      File f=new File(filePath);
      //MultipartFile 객체의 메소드를 통해서 실제로 이동시키기
      dto.getImage().transferTo(f);
    }catch (Exception e){
      e.printStackTrace();
    }
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    dto.setSaveFileName(saveFileName);
    dto.setWriter(userName);
    dao.insert(dto);
  }

  @Override
  public void list(Model model) {
   List<GalleryDto> list=dao.getList();
   model.addAttribute("list",list);
  }
}