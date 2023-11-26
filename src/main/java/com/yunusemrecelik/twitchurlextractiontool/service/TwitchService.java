package com.yunusemrecelik.twitchurlextractiontool.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yunusemrecelik.twitchurlextractiontool.exception.DataNotFoundException;
import com.yunusemrecelik.twitchurlextractiontool.exception.GetTokenException;
import com.yunusemrecelik.twitchurlextractiontool.model.TokenEntry;
import com.yunusemrecelik.twitchurlextractiontool.model.requests.TwitchOAuthRequest;
import com.yunusemrecelik.twitchurlextractiontool.model.responses.TwitchOAuthResponse;
import com.yunusemrecelik.twitchurlextractiontool.model.responses.TwitchSearchStreamResponse;
import com.yunusemrecelik.twitchurlextractiontool.model.responses.TwitchSearchUserResponse;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yunusemrecelik.twitchurlextractiontool.util.TokenCache.getCachedToken;
import static com.yunusemrecelik.twitchurlextractiontool.util.TokenCache.tokenCache;
import static io.restassured.RestAssured.given;

@Service
public class TwitchService implements ITwitchService {

    private final Gson gson = new Gson();
    private final String CONTENT_TYPE = "Content-Type";
    private final String CONTENT_TYPE_VALUE = "application/json";
    private final String ACCEPT = "Accept";
    private final String twitchApiUrl;
    private final String twitchClientId;
    private final String twitchClientSecret;

    public TwitchService(
            @Value("${twitch.api.url}") String twitchApiUrl,
            @Value("${twitch.api.clientId}") String twitchClientId,
            @Value("${twitch.api.clientSecret}") String twitchClientSecret) {
        this.twitchApiUrl = twitchApiUrl;
        this.twitchClientId = twitchClientId;
        this.twitchClientSecret = twitchClientSecret;
    }

    @Override
    public TwitchOAuthResponse getToken() {
        String path = "/oauth2/token";
        TwitchOAuthRequest requestBody = TwitchOAuthRequest.builder()
                .client_id(twitchClientId)
                .client_secret(twitchClientSecret)
                .grant_type("client_credentials")
                .build();

        RequestSpecification requestSpecification = given();
        requestSpecification.header(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        requestSpecification.header(ACCEPT, CONTENT_TYPE_VALUE);
        String jsonBody = gson.toJson(requestBody);
        requestSpecification.body(jsonBody);

        Response request = requestSpecification.post(twitchApiUrl + path);
        JsonPath responseBody = request.getBody().jsonPath();
        try {
            return TwitchOAuthResponse.builder().access_token(responseBody.get("access_token"))
                    .expires_in(responseBody.get("expires_in"))
                    .token_type(responseBody.get("token_type")).build();
        } catch (Exception e) {
            throw new GetTokenException("An error accorded when getting token from Twitch");
        }
    }

    @Override
    public List<TwitchSearchStreamResponse.SearchStreamData> getStreamDetails(String name) {
        String path = "/helix/streams";
        String query = "?user_login=" + name;

        RequestSpecification requestSpecification = given();
        String token = "";
        try {
            token = getCachedToken("twitchToken");
        } catch (NullPointerException e) {
            if (e.getMessage().contains("No cached token found")) {
                TwitchOAuthResponse response = getToken();
                token = response.getAccess_token();
                tokenCache.put("twitchToken", new TokenEntry(token, response.getToken_type(), response.getExpires_in()));
            }
        }

        requestSpecification.header("Authorization", "Bearer " + token);
        requestSpecification.header("Client-Id", twitchClientId);
        requestSpecification.header(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        requestSpecification.header(ACCEPT, CONTENT_TYPE_VALUE);

        try {
            Response response = requestSpecification.get(twitchApiUrl + path + query);
            TwitchSearchStreamResponse responseModel = new ObjectMapper()
                    .readValue(response.getBody().asString(), TwitchSearchStreamResponse.class);

            return responseModel.getData();
        } catch (Exception e) {
            throw new DataNotFoundException("An error accorded while trying to check Is Streamer Live");
        }
    }

    @Override
    public boolean isStreamerLive(String name) {
        return !getStreamDetails(name).isEmpty();
    }

    @Override
    public List<TwitchSearchUserResponse.SearchUserData> getUserDetails(String name) {
        String path = "/helix/users";
        String query = "?login=" + name;

        RequestSpecification requestSpecification = given();
        String token = "";
        try {
            token = getCachedToken("twitchToken");
        } catch (NullPointerException e) {
            if (e.getMessage().contains("No cached token found")) {
                TwitchOAuthResponse response = getToken();
                token = response.getAccess_token();
                tokenCache.put("twitchToken", new TokenEntry(token, response.getToken_type(), response.getExpires_in()));
            }
        }
        requestSpecification.header("Authorization", "Bearer " + token);
        requestSpecification.header("Client-Id", twitchClientId);
        requestSpecification.header(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        requestSpecification.header(ACCEPT, CONTENT_TYPE_VALUE);

        try {
            Response response = requestSpecification.get(twitchApiUrl + path + query);
            TwitchSearchUserResponse responseModel = new ObjectMapper()
                    .readValue(response.getBody().asString(), TwitchSearchUserResponse.class);

            return responseModel.getData();
        } catch (Exception e) {
            throw new DataNotFoundException("An error accorded while trying to Get User Details");
        }
    }

    @Override
    public boolean isUserExists(String name) {
        return !getUserDetails(name).isEmpty();
    }
}
