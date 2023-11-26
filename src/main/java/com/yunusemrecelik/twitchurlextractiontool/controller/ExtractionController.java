package com.yunusemrecelik.twitchurlextractiontool.controller;

import com.yunusemrecelik.twitchurlextractiontool.exception.DataNotFoundException;
import com.yunusemrecelik.twitchurlextractiontool.model.responses.TwitchSearchStreamResponse;
import com.yunusemrecelik.twitchurlextractiontool.service.ExtractorService;
import com.yunusemrecelik.twitchurlextractiontool.service.TwitchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/search/stream")
public class ExtractionController {

    @Autowired
    private ExtractorService extractorService;

    @Autowired
    private TwitchService twitchService;

    private final Logger logger = LoggerFactory.getLogger(ExtractionController.class);

    private void checkUserExists(String name) {
        logger.info("Searching {} on Twitch...", name);
        if (!twitchService.isUserExists(name)) {
            throw new NullPointerException("User " + name + " does not exist on Twitch.");
        }
        logger.info("{} is found on Twitch!", name);
    }

    private void checkStreamerIsLive(String name) {
        logger.info("Checking if {} is live...", name);
        if (!twitchService.isStreamerLive(name)) {
            throw new DataNotFoundException(name + " is not online right now");
        }
        logger.info("{} is live!", name);
    }

    @GetMapping("/{name}/details")
    public ResponseEntity<List<TwitchSearchStreamResponse.SearchStreamData>> getStreamDetails(@PathVariable String name) {
        name = name.toLowerCase(Locale.ENGLISH);
        checkUserExists(name);
        checkStreamerIsLive(name);

        return ResponseEntity.ok(twitchService.getStreamDetails(name));
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<HashMap<String, String>>> getStreamUrls(@PathVariable String name) {
        name = name.toLowerCase(Locale.ENGLISH);
        checkUserExists(name);
        checkStreamerIsLive(name);

        return ResponseEntity.ok(extractorService.getStreamUrls(name));
    }
}

