package com.yunusemrecelik.twitchurlextractiontool.model.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TwitchSearchUserResponse {
    private List<SearchUserData> data;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchUserData {
        private String id;
        private String login;
        private String display_name;
        private String type;
        private String broadcaster_type;
        private String description;
        private String profile_image_url;
        private String offline_image_url;
        private int view_count;
        private String created_at;
    }
}
