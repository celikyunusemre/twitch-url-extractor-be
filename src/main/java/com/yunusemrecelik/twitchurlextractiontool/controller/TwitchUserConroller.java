package com.yunusemrecelik.twitchurlextractiontool.controller;

import com.yunusemrecelik.twitchurlextractiontool.model.responses.TwitchSearchUserResponse;
import com.yunusemrecelik.twitchurlextractiontool.service.TwitchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/search/user")
public class TwitchUserConroller {
    @Autowired
    private TwitchService twitchService;

    private final Logger logger = LoggerFactory.getLogger(TwitchUserConroller.class);

    private void checkUserExists(String name) {
        logger.info("Searching {} on Twitch...", name);
        if (!twitchService.isUserExists(name)) {
            throw new NullPointerException("User " + name + " does not exist on Twitch.");
        }
        logger.info("{} is found on Twitch!", name);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<TwitchSearchUserResponse.SearchUserData>> getStreamer(@PathVariable String name) {
        name = name.toLowerCase(Locale.ENGLISH);
        checkUserExists(name);

        return ResponseEntity.ok(twitchService.getUserDetails(name));
    }
}
