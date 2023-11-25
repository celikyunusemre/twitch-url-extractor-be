package com.yunusemrecelik.twitchurlextractiontool.util;

import com.yunusemrecelik.twitchurlextractiontool.service.TwitchService;

public class TokenProvider implements ITokenProvider {
    private final TwitchService twitchService = new TwitchService();

    @Override
    public String fetchToken() {
        return twitchService.getToken().getAccess_token();
    }

    @Override
    public String fetchTokenType() {
        return twitchService.getToken().getToken_type();
    }

    @Override
    public String fetchTokenExpire() {
        return String.valueOf(twitchService.getToken().getExpires_in());
    }
}
