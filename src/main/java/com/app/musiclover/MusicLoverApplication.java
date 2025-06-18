package com.app.musiclover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MusicLoverApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicLoverApplication.class, args);
    }

}
