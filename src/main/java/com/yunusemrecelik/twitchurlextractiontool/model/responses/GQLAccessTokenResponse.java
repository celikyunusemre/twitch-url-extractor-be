package com.yunusemrecelik.twitchurlextractiontool.model.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.HashMap;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GQLAccessTokenResponse {
    private GQLAccessTokenData data;
    private HashMap<String, Object> extensions;
}
