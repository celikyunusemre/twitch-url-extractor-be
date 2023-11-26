package com.yunusemrecelik.twitchurlextractiontool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class TwitchUrlExtractionToolApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(TwitchUrlExtractionToolApplication.class, args);
    }

}
