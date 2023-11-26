package com.yunusemrecelik.twitchurlextractiontool.util;

import com.yunusemrecelik.twitchurlextractiontool.exception.GetTokenException;
import com.yunusemrecelik.twitchurlextractiontool.model.responses.TwitchOAuthResponse;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TokenCache {

    private static final Logger logger = LoggerFactory.getLogger(TokenCache.class);

    /**
     * Cached tokens.
     */
    public static final Map<String, TwitchOAuthResponse> tokenCache = new HashMap<>();

    /**
     * Gets the cached token.
     * @param key the map's key to get cached token
     * @return string the token
     */
    public static String getCachedToken(String key) {
        TwitchOAuthResponse entry = tokenCache.get(key);

        if (entry != null && isValidToken(entry.getAccess_token())) {
            logger.info("Token is valid!");
            return entry.getAccess_token();
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
        logger.info("Checking is token valid...");
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
