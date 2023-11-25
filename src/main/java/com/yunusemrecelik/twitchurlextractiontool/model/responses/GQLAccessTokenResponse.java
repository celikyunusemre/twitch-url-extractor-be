package com.yunusemrecelik.twitchurlextractiontool.model.responses;

import lombok.*;

import java.util.HashMap;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GQLAccessTokenResponse {
    private HashMap<String, HashMap<String, String>> data;
    private HashMap<String, String> extensions;
}
