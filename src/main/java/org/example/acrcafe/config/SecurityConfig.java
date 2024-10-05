package org.example.acrcafe.config;

import org.example.acrcafe.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  //jwt 를 쿠키로 저장할때 쿠키의 이름
  @Value("${jwt.name}")
  private String jwtName;

  @Autowired
  private JwtFilter jwtFilter;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http.csrf(csrf->csrf.disable())
        .authorizeHttpRequests(config->config
            .requestMatchers("/").permitAll()
            .requestMatchers("/super").hasAnyRole("super")
            .requestMatchers("/user").hasAnyRole("user")
            .anyRequest().authenticated()
        )
        .sessionManagement(config->config
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
  @Bean
  PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
