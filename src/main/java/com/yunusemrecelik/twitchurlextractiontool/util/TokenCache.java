package com.yunusemrecelik.twitchurlextractiontool.util;

import com.yunusemrecelik.twitchurlextractiontool.exception.UnexpectedErrorException;
import com.yunusemrecelik.twitchurlextractiontool.model.TokenEntry;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TokenCache {

    public static final Map<String, TokenEntry> tokenCache = new HashMap<>();

    public static String getCachedToken(String key) {
        TokenEntry entry = tokenCache.get(key);

        if (entry != null && isValidToken(entry.token)) {
            return entry.token;
        }
        throw new UnexpectedErrorException("No cached token found");
//        try {
//            // Cache miss or expired entry, fetch the new token and update the cache
//            String newToken = tokenProvider.fetchToken();
//            String newTokenType = tokenProvider.fetchTokenType();
//            String newTokenExpire = tokenProvider.fetchTokenExpire();
//            tokenCache.put(key, new TokenEntry(newToken, newTokenType, newTokenExpire));
//            return newToken;
//        } catch (Exception e) {
//            throw new UnexpectedErrorException("An error occurred while getting Cached Token: " + e.getMessage());
//        }
    }

    private static boolean isValidToken(String token) {
        // Perform token validation logic here
        // For example, make a request to the token validation endpoint
        // and check if the response status code is 200
        // You can customize this logic based on your specific requirements
        try {
            Response response = given().header("Authorization", "OAuth " + token)
                    .get("https://id.twitch.tv/oauth2/validate");
            return response.statusCode() == 200;
        } catch (Exception e) {
            // Handle exception or log the error
            return false;
        }
    }

}
