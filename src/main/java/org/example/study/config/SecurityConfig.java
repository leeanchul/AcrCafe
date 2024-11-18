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
                                         CookieRequestCache cookCache) throws Exception {
    String[] whiteList = {"/", "/user/signupform", "/user/signup", "/favicon.ico", "/error",
        "/user/loginform", "/user/login_fail", "/user/expired",
        "/gallery/list", "/gallery/detail", "/upload/images/**"
        , "/upload/**", "/test/list",
        "/cafe/list", "/caf/detail", "/test/comment_list", "/test/list", "/test/detail/**",
        "/api/**"};

    hs.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(config -> config
            .requestMatchers(whiteList).permitAll()
            .requestMatchers("user").hasAnyRole("USER")
            .anyRequest().authenticated())
        .sessionManagement(config ->
            config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin(config -> config
            .loginPage("/user/required_loginform")
            .loginProcessingUrl("/user/login")
            .usernameParameter("userName")
            .passwordParameter("password")
            .successHandler(successHandler)
            .failureForwardUrl("/user/login_fail")
            .failureHandler(failHandler)
            .permitAll())
        .logout(config -> config
            .logoutUrl("/user/logout")
            .logoutSuccessHandler((request, response, auth) -> {
              Cookie cook = new Cookie(jwtName, null);
              // 쿠키를 삭제하기 위해 setMaxAge(0)
              cook.setMaxAge(0);
              cook.setPath("/");
              response.addCookie(cook);
              response.sendRedirect(request.getContextPath() + "/");
            })
            .permitAll())
        .exceptionHandling(config ->
            config.accessDeniedPage("/user/denied"))
        .addFilterBefore(jwtFilter,
            UsernamePasswordAuthenticationFilter.class)
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
