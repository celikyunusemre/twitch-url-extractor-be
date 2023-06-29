package com.yunusemrecelik.twitchurlextractiontool.controller;

import com.yunusemrecelik.twitchurlextractiontool.exception.ApiRequestException;
import com.yunusemrecelik.twitchurlextractiontool.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class ToolController {

    @Autowired
    private ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping()
    @CrossOrigin(origins = "*")
    public Object getUrl(@RequestParam String name) {
        List<HashMap<String, String>> streamUrls = toolService.getStream(name);
        if (streamUrls.isEmpty()) {
            throw new ApiRequestException("Channel is offline or invalid channel name");
        } else {
            return streamUrls;
        }
    }
}
