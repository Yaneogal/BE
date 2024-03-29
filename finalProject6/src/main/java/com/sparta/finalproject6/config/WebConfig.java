package com.sparta.finalproject6.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://yaneogal.site", "https://www.yaneogal.site")
                .allowedMethods("GET","POST","DELETE","PUT")
                .exposedHeaders("*")
                .allowCredentials(true);
    }
}
