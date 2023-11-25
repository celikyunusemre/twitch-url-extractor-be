package com.yunusemrecelik.twitchurlextractiontool.controller;

import com.yunusemrecelik.twitchurlextractiontool.exception.EmptyChannelNameException;
import com.yunusemrecelik.twitchurlextractiontool.exception.EmptyUrlException;
import com.yunusemrecelik.twitchurlextractiontool.exception.NonExistUserException;
import com.yunusemrecelik.twitchurlextractiontool.exception.UnexpectedErrorException;
import com.yunusemrecelik.twitchurlextractiontool.service.ExtractorService;
import com.yunusemrecelik.twitchurlextractiontool.service.TwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;


@RestController
public class ExtractionController {

    @Autowired
    private ExtractorService extractorService;

    @Autowired
    private TwitchService twitchService;

    @GetMapping()
    @CrossOrigin(origins = "*")
    public Object getUrl(@RequestParam String name) {

        if (name.isEmpty() || name.isBlank()) {
            throw new EmptyChannelNameException("Channel name is required");
        }

        name = name.toLowerCase(Locale.ENGLISH);

        if (!twitchService.isUserExists(name)) {
            throw new NonExistUserException("This streamer does not exist on Twitch.tv");
        }

        if (!twitchService.isStreamerLive(name)) {
            throw new EmptyUrlException("Channel is offline");
        }

        try {
            List<HashMap<String, String>> streamUrls = extractorService.getStreamUrls(name);
            if (streamUrls.isEmpty()) {
                throw new UnexpectedErrorException("Failed to get stream urls!!");
            }
            return streamUrls;
        } catch (Exception e) {
            throw new UnexpectedErrorException("An error accorded while trying to get Stream URLs: " + e.getMessage());
        }

    }
}
