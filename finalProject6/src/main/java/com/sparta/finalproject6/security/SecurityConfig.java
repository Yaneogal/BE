package com.sparta.finalproject6.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        //UsernamePasswordAuthenticationFilter에 도달했을때 AbstractAuthenticationProcessingFilter클래스를 통해
        // 로그인 URL인지 확인하고 로그인 요청이 확인 될 시 아이디 패스워드를 가져와서 인증을 위한 객체를 생성한다.

        httpSecurity.csrf().disable()
                .cors()
                .and()
                //h2 콘솔을 사용하기 위해 옵션을 disable
                .headers().frameOptions().disable()
                .and().authorizeRequests()
                .anyRequest().permitAll()
                //Spring Security에서 session을 생성하거나 사용하지 않도록 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);



        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }
}
