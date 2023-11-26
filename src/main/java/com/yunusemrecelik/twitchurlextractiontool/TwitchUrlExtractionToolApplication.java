package com.yunusemrecelik.twitchurlextractiontool;

import com.yunusemrecelik.twitchurlextractiontool.model.TokenEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import static com.yunusemrecelik.twitchurlextractiontool.util.TokenCache.tokenCache;

@SpringBootApplication
public class TwitchUrlExtractionToolApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(TwitchUrlExtractionToolApplication.class, args);
    }

    @Bean
    public void qwe(
    ) {
        tokenCache.put("twitchToken", new TokenEntry(
                this.environment.getProperty("test_token"),
                "bearer",
                "123123123"));
    }

}
