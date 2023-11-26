package com.yunusemrecelik.twitchurlextractiontool.util;

import com.yunusemrecelik.twitchurlextractiontool.exception.GetTokenException;
import com.yunusemrecelik.twitchurlextractiontool.model.TokenEntry;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TokenCache {

    /**
     * Cached tokens.
     */
    public static final Map<String, TokenEntry> tokenCache = new HashMap<>();

    /**
     * Gets the cached token.
     * @param key the map's key to get cached token
     * @return string the token
     */
    public static String getCachedToken(String key) {
        TokenEntry entry = tokenCache.get(key);

        if (entry != null && isValidToken(entry.token)) {
            return entry.token;
        }
        throw new NullPointerException("No cached token found");
    }

    /**
     * Checks if the token is valid
     * @param token the twitch token
     * @return boolean
     */
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
            throw new GetTokenException("An error accorded while checking Is Token Valid");
        }
    }

}
