package com.yunusemrecelik.twitchurlextractiontool.service;

import com.yunusemrecelik.twitchurlextractiontool.model.responses.TwitchOAuthResponse;
import com.yunusemrecelik.twitchurlextractiontool.model.responses.TwitchSearchUserResponse;

import java.util.List;

public interface ITwitchService {
    TwitchOAuthResponse getToken();

    boolean isStreamerLive(String name);

    boolean isUserExists(String name);

    List<TwitchSearchUserResponse.SearchUserData> getUserDetails(String name);
}
