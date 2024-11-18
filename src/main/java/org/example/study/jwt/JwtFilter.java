package org.example.study.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.study.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//요청할때마다 한번 거치는 필터 만들기 (스프링 프레임워크 내에서 동작하는 필터)
@Component
public class JwtFilter extends OncePerRequestFilter {

  //jwt 를 쿠키로 저장할때 쿠키의 이름
  @Value("${jwt.name}")
  private String jwtName;
  //JwtUtil 객체 주입 받기
  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private CustomUserDetailService service;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // 쿠키에서 JWT 토큰 추출
    Cookie[] cookies = request.getCookies();

    // 쿠키에 JWT 토큰이 없으면 빈 문자열로 초기화
    String jwtToken = "";

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (jwtName.equals(cookie.getName())) {
          jwtToken = cookie.getValue();
          break;
        }
      }
    }

    // 사용자명 변수 선언
    String userName = null;

    // 토큰이 Bearer로 시작하는지 확인
    if (jwtToken.startsWith("Bearer ")) {
      // "Bearer "를 제외한 순수 토큰 문자열 얻기
      jwtToken = jwtToken.substring(7);

      // 유틸을 이용해서 토큰에 저장된 userName (subject) 추출
      userName = jwtUtil.extractUsername(jwtToken);
    }

    // userName이 존재하고 Spring Security에서 인증되지 않은 상태라면
    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      // DB에서 UserDetails 객체를 가져오기
      UserDetails userDetails = service.loadUserByUsername(userName);

      // 토큰이 유효한지 유틸을 통해 확인
      boolean isValid = jwtUtil.validateToken(jwtToken, userDetails);

      // 토큰이 유효하다면 1회성 로그인 처리
      if (isValid) {
        // 사용자 인증 자격 증명을 저장
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Security 컨텍스트 업데이트 (1회성 로그인)
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    // JwtFilter 수행됨
    System.out.println("JwtFilter 수행됨");

    // 다음 spring 필터 chain 진행
    filterChain.doFilter(request, response);
  }


}