package com.yunusemrecelik.twitchurlextractiontool.util;

public interface ITokenProvider {
    String fetchToken();

    String fetchTokenType();

    String fetchTokenExpire();
}
