package com.sparta.finalproject6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@SuppressWarnings("unchecked")
@EnableJpaAuditing
public class FinalProject6Application {

    public static void main(String[] args) {
        SpringApplication.run(FinalProject6Application.class, args);
    }

}
