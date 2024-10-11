package org.example.study.config;

import jakarta.servlet.http.Cookie;
import org.example.study.handler.AuthFailHandler;
import org.example.study.handler.AuthSuccessHandler;
import org.example.study.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.CookieRequestCache;

@Configuration //설정 파일이라고 알리기
@EnableWebSecurity
public class SecurityConfig {

  //jwt 를 쿠키로 저장할때 쿠키의 이름
  @Value("${jwt.name}")
  private String jwtName;

  @Autowired
  private JwtFilter jwtFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity hs, AuthSuccessHandler successHandler,
                                         AuthFailHandler failHandler,
                                         CookieRequestCache cookCache) throws Exception{
    String[] whiteList= {"/", "/user/signupform", "/user/signup","/favicon.ico","/error",
        "/user/loginform", "/user/login_fail", "/user/expired",
        "/gallery/list", "/gallery/detail", "/upload/images/**"
        ,"/upload/**","/test/list",
        "/cafe/list", "/caf/detail","/test/comment_list","/test/list",
        "/api/**"};

    hs.csrf(csrf->csrf.disable())
        .authorizeHttpRequests(config->config
            .requestMatchers(whiteList).permitAll()
            .requestMatchers("user").hasAnyRole("USER")
            .anyRequest().authenticated()
        )
        .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin(config->config
                .loginPage("/user/required_loginform")
                .loginProcessingUrl("/user/login")
                .usernameParameter("userName")
                .passwordParameter("password")
                .successHandler(successHandler)
    //.failureForwardUrl("/user/login_fail") //로그인 실패시 forward 될 url 설정
                .failureHandler(failHandler) //로그인 실패 핸들러 등록
                .permitAll()
            )
        .logout(config->config
            .logoutUrl("/user/logout")
            .logoutSuccessHandler((request, response, auth)->{
              Cookie cook=new Cookie(jwtName, null);
              //쿠키를 삭제하기 위해 setMaxAge(0)
              cook.setMaxAge(0);
              cook.setPath("/");
              response.addCookie(cook);
              //쿠키 삭제후에 최상위 경로로 리다일렉트 이동
              response.sendRedirect(request.getContextPath()+"/");
            })
            .permitAll()
        )
        .exceptionHandling(config ->
            //403 forbidden 인 경우 forward 이동 시킬 경로 설정
            config.accessDeniedPage("/user/denied")
        )
        //토큰을 검사하는 필터를 security filter 가 동작하기 이전에 동작하도록 설정 한다.
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        //세션을 사용할수 없기때문에 쿠키케시를 사용하도록 설정한다.
        .requestCache(config -> config.requestCache(cookCache));

    return hs.build();
  }
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  CookieRequestCache getCookieRequestCache() {
    return new CookieRequestCache();
  }

}
