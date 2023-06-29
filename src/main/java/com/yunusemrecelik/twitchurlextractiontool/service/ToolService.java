package com.yunusemrecelik.twitchurlextractiontool.service;

import com.google.gson.Gson;
import com.yunusemrecelik.twitchurlextractiontool.model.GetAccessTokenRequestModel;
import com.yunusemrecelik.twitchurlextractiontool.model.GetAccessTokenResponseModel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Service
public class ToolService {
    private final String clientId;
    private String getAccessTokenUrl = "https://gql.twitch.tv:443/gql";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";
    private static final String ACCEPT = "Accept";

    public ToolService() {
        this.clientId = "kimne78kx3ncx6brgo4mv6wki5h1ko";
    }

    public ToolService(String clientId) {
        this.clientId = clientId;
    }

    private GetAccessTokenResponseModel getAccessToken(String name) {
        Gson gson = new Gson();
        RequestSpecification requestSpecification = given();
        requestSpecification.header(CONTENT_TYPE_VALUE, CONTENT_TYPE_VALUE);
        requestSpecification.header(ACCEPT, "*/*");
        requestSpecification.header("Client-ID", this.clientId);

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

        GetAccessTokenRequestModel tokenModel = GetAccessTokenRequestModel.builder()
                .operationName("PlaybackAccessToken")
                .extensions(extensions)
                .variables(variables)
                .build();
        String jsonBody = gson.toJson(tokenModel);
        requestSpecification.body(jsonBody);
        Response response = requestSpecification.post(getAccessTokenUrl);
        JsonPath responseBody = response.getBody().jsonPath();


        return GetAccessTokenResponseModel.builder()
                .data(responseBody.get("data"))
                .extensions(responseBody.get("extensions"))
                .build();
    }

    private String getPlaylist(String name, GetAccessTokenResponseModel token) {
        RequestSpecification requestSpecification = given();
        requestSpecification.header(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        requestSpecification.header(ACCEPT, CONTENT_TYPE_VALUE);

        Response response = requestSpecification
                .queryParam("client_id", this.clientId)
                .queryParam("token", token.getData().get("streamPlaybackAccessToken").get("value"))
                .queryParam("sig", token.getData().get("streamPlaybackAccessToken").get("signature"))
                .queryParam("allow_source", true)
                .queryParam("allow_audio_only", true)
                .get("https://usher.ttvnw.net/api/channel/hls/"+name+".m3u8");
        return response.getBody().print();
    }

    public List<HashMap<String, String>> getStream(String name) {
        GetAccessTokenResponseModel accessToken = getAccessToken(name);
        return parsePlayList(String.valueOf(getPlaylist(name, accessToken)));
    }

    private List<HashMap<String, String>> parsePlayList(String playList) {
        List<HashMap<String, String>> parsedPlayList = new ArrayList<>();
        String[] lines = playList.split("\n");
        for (int i = 4; i < lines.length ; i += 3) {
            HashMap<String, String> properties = new HashMap<>();
            properties.put("quality", lines[i - 2].split("NAME=")[1].split("\"")[1]);
            properties.put("resolution", (lines[i - 1].indexOf("RESOLUTION") != -1 ? lines[i - 1].split("RESOLUTION=")[1].split(",")[0] : null));
            properties.put("url", lines[i]);
            parsedPlayList.add(properties);
        }
        return parsedPlayList;
    }
}
