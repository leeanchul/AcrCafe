package org.example.study.service;

import org.example.study.dto.CafeDto;
import org.example.study.exception.NotOwnerException;
import org.example.study.repository.CafeDao;
import org.example.study.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class CafeServiceImpl implements CafeService {
  @Autowired
  private CafeDao dao;

  @Autowired
  private UserDao user;

  @Override
  public void insert(CafeDto dto) {
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    dto.setWriter(userName);
    //DB 에 저장
    dao.insert(dto);
  }

  @Override
  public void getList(Model model, CafeDto dto) {
    List<CafeDto> list = dao.getList(dto);
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();

    model.addAttribute("list", list);
    model.addAttribute("userName", userName);
  }

  @Override
  public void delete(int num) {
    //현재 로그인 한 사용자에 id 를 가져오기.
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    String writer = dao.getDto(num).getWriter();
    if (!userName.equals(writer)) {
      throw new NotOwnerException("글 작성자와 일치 하지 않습니다");
    }
    dao.delete(num);
  }

  @Override
  public void update(CafeDto dto) {
    dao.update(dto);
  }

  @Override
  public void getDto(Model model, int num) {
    CafeDto dto = dao.getDto(num);

    //현재 로그인 한 사용자에 id 를 가져오기.
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    String writer = dto.getWriter();
    if (!writer.equals(userName)) {
      throw new NotOwnerException("글 작성자와 일치 하지 않습니다.");
    }
    model.addAttribute("dto", dto);
  }
}
