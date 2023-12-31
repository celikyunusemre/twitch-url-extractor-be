package com.yunusemrecelik.twitchurlextractiontool.model.responses;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TwitchOAuthResponse {
    private String access_token;
    private int expires_in;
    private String token_type;
}