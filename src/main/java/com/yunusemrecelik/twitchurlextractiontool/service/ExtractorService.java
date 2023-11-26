package com.yunusemrecelik.twitchurlextractiontool.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yunusemrecelik.twitchurlextractiontool.model.requests.GQLAccessTokenRequest;
import com.yunusemrecelik.twitchurlextractiontool.model.responses.GQLAccessTokenResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Service
public class ExtractorService implements IExtractorService {

    /**
     *  Content Type value -> application/json by default
     */
    private final String CONTENT_TYPE_VALUE = "application/json";
    /**
     * Header for endpoint requests
     */
    private final String ACCEPT = "Accept";
    private final Gson gson = new Gson();
    /**
     * Twitch GQL Url
     */
    @Value("${twitch.gql.url}")
    private String twitchGQLUrl;
    /**
     * Cliend ID of Twitch QGL
     */
    @Value("${twitch.gql.clientId}")
    private String twitchGQLClientId;


    /**
     * @param name the streamer name
     * @return GQLAccessTokenResponse returns response model of gql access request
     */
    private GQLAccessTokenResponse getAccessToken(String name) {
        RequestSpecification requestSpecification = given();
        requestSpecification.header(ACCEPT, "*/*");
        requestSpecification.header("Client-ID", twitchGQLClientId);

        Map<String, Object> persistedQuery = new HashMap<>();
        persistedQuery.put("version", 1);
        persistedQuery.put("sha256Hash", "0828119ded1c13477966434e15800ff57ddacf13ba1911c129dc2200705b0712");

        Map<String, Map<String, Object>> extensions = new HashMap<>();
        extensions.put("persistedQuery", persistedQuery);

        Map<String, Object> variables = new HashMap<>();
        variables.put("isLive", true);
        variables.put("login", name);
        variables.put("isVod", false);
        variables.put("vodID", "");
        variables.put("playerType", "embed");

        GQLAccessTokenRequest requestModel = GQLAccessTokenRequest.builder()
                .operationName("PlaybackAccessToken")
                .extensions(extensions)
                .variables(variables)
                .build();
        String requestBody = gson.toJson(requestModel);
        requestSpecification.body(requestBody);

        try {
            Response response = requestSpecification.post(twitchGQLUrl);

            return new ObjectMapper()
                    .readValue(response.getBody().asString(), GQLAccessTokenResponse.class);
        } catch (Exception e) {
            throw new NullPointerException("An error accorded while trying to get GQL Access Token: " + e.getMessage());
        }
    }

    public String getPlaylist(String name, GQLAccessTokenResponse gqlAccessTokenResponse) {
        RequestSpecification requestSpecification = given();
        requestSpecification.header("Content-Type", CONTENT_TYPE_VALUE);
        requestSpecification.header(ACCEPT, CONTENT_TYPE_VALUE);

        try {
            Response response = requestSpecification
                    .queryParam("client_id", twitchGQLClientId)
                    .queryParam("token", gqlAccessTokenResponse.getData().get("streamPlaybackAccessToken").get("value"))
                    .queryParam("sig", gqlAccessTokenResponse.getData().get("streamPlaybackAccessToken").get("signature"))
                    .queryParam("allow_source", true)
                    .queryParam("allow_audio_only", true)
                    .get("https://usher.ttvnw.net/api/channel/hls/" + name + ".m3u8");
            return response.getBody().asString();
        } catch (Exception e) {
            throw new NullPointerException("An error accorded while trying to Get Playlists: " + e.getMessage());
        }
    }

    private List<HashMap<String, String>> parsePlaylist(String playList) {
        List<HashMap<String, String>> parsedPlayList = new ArrayList<>();
        String[] lines = playList.split("\n");
        for (int i = 4; i < lines.length; i += 3) {
            HashMap<String, String> properties = new HashMap<>();
            properties.put("quality", lines[i - 2].split("NAME=")[1].split("\"")[1]);
            properties.put("resolution", (lines[i - 1].indexOf("RESOLUTION") != -1 ? lines[i - 1].split("RESOLUTION=")[1].split(",")[0] : null));
            properties.put("url", lines[i]);
            parsedPlayList.add(properties);
        }
        return parsedPlayList;
    }

    @Override
    public List<HashMap<String, String>> getStreamUrls(String name) {
        GQLAccessTokenResponse accessToken = getAccessToken(name);
        return parsePlaylist(getPlaylist(name, accessToken));
    }
}
