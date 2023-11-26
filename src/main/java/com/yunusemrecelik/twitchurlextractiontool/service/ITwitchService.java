package com.yunusemrecelik.twitchurlextractiontool.service;

import com.yunusemrecelik.twitchurlextractiontool.model.responses.TwitchOAuthResponse;
import com.yunusemrecelik.twitchurlextractiontool.model.responses.TwitchSearchStreamResponse;
import com.yunusemrecelik.twitchurlextractiontool.model.responses.TwitchSearchUserResponse;

import java.util.List;

public interface ITwitchService {
    boolean isStreamerLive(String name);

    boolean isUserExists(String name);
    TwitchOAuthResponse getToken();

    TwitchSearchStreamResponse getStreamDetails(String name);

    TwitchSearchUserResponse getUserDetails(String name);
}
