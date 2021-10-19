package com.team13.teamplay2insta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Teamplay2InstaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Teamplay2InstaApplication.class, args);
    }

}
