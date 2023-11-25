package com.yunusemrecelik.twitchurlextractiontool.model.requests;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GQLAccessTokenRequest {
    private String operationName;
    private Map<String, Map<String, Object>> extensions;
    private Map<String, Object> variables;
}
