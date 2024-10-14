package org.example.study.service;

import org.example.study.dto.TestCommentDto;
import org.example.study.dto.TestDto;
import org.example.study.repository.TestCommentDao;
import org.example.study.repository.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class TestServiceImpl implements TestService{

  //한 페이지에 글을 몇개씩 표시할 것인지
  final int PAGE_ROW_COUNT=5;
  //하단 페이지 UI를 몇개씩 표시할 것인지
  final int PAGE_DISPLAY_COUNT=5;

  @Autowired
  private TestDao dao;

  @Autowired
  private TestCommentDao commentDao;
  //파일을 저장할 위치
  @Value("${file.location}")
  private String fileLocation;

  @Override
  public void insert(TestDto dto) {
    MultipartFile image=dto.getImage();
    if(image.getSize() != 0) {
      //1.업로도된 파일 저장
      //저장할 파일의 이름 겹치지 않는 유일한 문자열로 얻어내기
      String saveFileName = UUID.randomUUID().toString();
      //저장할 파일의 전체 경로 구성하기
      String filePath = fileLocation + File.separator + saveFileName;
      try {
        //업로드된 파일을 이동시킬 목저지 파일 객체
        File f = new File(filePath);
        //MultipartFile 객체의 메소드를 통해서 실제로 이동시키기
        dto.getImage().transferTo(f);
      } catch (Exception e) {
        e.printStackTrace();
      }
      dto.setSaveFileName(saveFileName);
    }else{
      dto.setSaveFileName("null");
    }
      String userName = SecurityContextHolder.getContext().getAuthentication().getName();
      dto.setWriter(userName);

    dao.insert(dto);
  }

  @Override
  public void list(Model model,TestDto dto) {
// pageNum 에 해당하는 글정보를 select 에서 Model 객체에 담는 작업을 하면 된다.

    int pageNum=dto.getPageNum();
    //보여줄 페이지의 시작 ROWNUM
    int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
    //보여줄 페이지의 끝 ROWNUM
    int endRowNum=pageNum*PAGE_ROW_COUNT;

    //하단 시작 페이지 번호
    int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
    //하단 끝 페이지 번호
    int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
    //전체 글의 갯수
    int totalRow=dao.getCount(dto);
    //전체 페이지의 갯수 구하기
    int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
    //끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
    if(endPageNum > totalPageCount){
      endPageNum=totalPageCount; //보정해 준다.
    }
    //계산된 startRowNum과 endRowNum을 dto에 담고
    dto.setStartRowNum(startRowNum);
    dto.setEndRowNum(endRowNum);
    //TestDto 인자로 전달해서 글 목록 얻어오기
    List<TestDto> list=dao.getList(dto);
    model.addAttribute("comment",list);
    // view page 에 전달할 내용을 Model 객체에 담는다.
    model.addAttribute("list", list);
    model.addAttribute("startPageNum", startPageNum);
    model.addAttribute("endPageNum", endPageNum);
    model.addAttribute("totalPageCount", totalPageCount);
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("dto", dto); //키워드정보가 들어 있는 dto 를 모델에 담기
    model.addAttribute("totalRow", totalRow);
  }

  @Override
  public void getDto(Model model, int num) {
    TestDto dto=dao.getDto(num);
    model.addAttribute("dto",dto);
  }

  @Override
  public void commentList(Model model, int ref_group) {
    List<TestCommentDto> list=commentDao.getCommnetList(ref_group);
    model.addAttribute("comment",list);
  }

  @Override
  public void commentInsert(TestCommentDto dto) {
    String writer= SecurityContextHolder.getContext().getAuthentication().getName();
    dto.setWriter(writer);
    commentDao.insert(dto);
  }

}
