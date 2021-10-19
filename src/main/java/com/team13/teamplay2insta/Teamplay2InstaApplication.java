package com.team13.teamplay2insta;

import com.team13.teamplay2insta.model.Post;
import com.team13.teamplay2insta.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Teamplay2InstaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Teamplay2InstaApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(PostRepository repository) {
//        return (args) -> {
//            repository.save(new Post(null, "이미지", "아무거나"));
//        };
//
//    }
}
