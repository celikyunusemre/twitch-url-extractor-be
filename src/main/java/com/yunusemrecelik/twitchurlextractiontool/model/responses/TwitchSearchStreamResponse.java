package com.yunusemrecelik.twitchurlextractiontool.model.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitchSearchStreamResponse {
    private List<SearchStreamData> data;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchStreamData {
        private String id;
        private String user_id;
        private String user_login;
        private String user_name;
        private String game_id;
        private String game_name;
        private String type;
        private String title;
        private List<String> tags;
        private int viewer_count;
        private String started_at;
        private String language;
        private String thumbnail_url;
        private List<String> tag_ids;
        private boolean is_mature;

    }
}