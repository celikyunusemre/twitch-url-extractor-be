package com.yunusemrecelik.twitchurlextractiontool.util;

import com.yunusemrecelik.twitchurlextractiontool.exception.UnexpectedErrorException;
import com.yunusemrecelik.twitchurlextractiontool.model.TokenEntry;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TokenCache {

    public static final Map<String, TokenEntry> tokenCache = new HashMap<>();

    public static String getCachedToken(String key, ITokenProvider tokenProvider) {
        TokenEntry entry = tokenCache.get(key);

        try {
            RequestSpecification requestSpecification = given();
            requestSpecification.header("Authorization", "OAuth " + entry.token);
            Response response = requestSpecification.get("https://id.twitch.tv/oauth2/validate");
            if (response.statusCode() == 200) {
                return entry.token;
            } else {
                // Cache miss or expired entry, fetch the new token and update the cache
                String newToken = tokenProvider.fetchToken();
                String newTokenType = tokenProvider.fetchTokenType();
                String newTokenExpire = tokenProvider.fetchTokenExpire();
                tokenCache.put(key, new TokenEntry(newToken, newTokenType, newTokenExpire));
                return newToken;
            }
        } catch (NullPointerException nullPointerException) {
            // Cache miss or expired entry, fetch the new token and update the cache
            String newToken = tokenProvider.fetchToken();
            String newTokenType = tokenProvider.fetchTokenType();
            String newTokenExpire = tokenProvider.fetchTokenExpire();
            tokenCache.put(key, new TokenEntry(newToken, newTokenType, newTokenExpire));
            return newToken;
        } catch (Exception e) {
            throw new UnexpectedErrorException("An error accorded while getting Cached Token: " + e.getMessage());
        }
    }
}
