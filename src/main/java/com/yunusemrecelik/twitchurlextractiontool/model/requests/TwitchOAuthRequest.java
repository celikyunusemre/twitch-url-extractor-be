package com.yunusemrecelik.twitchurlextractiontool.model.requests;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TwitchOAuthRequest {
    private String client_id;
    private String client_secret;
    private String grant_type;
}
