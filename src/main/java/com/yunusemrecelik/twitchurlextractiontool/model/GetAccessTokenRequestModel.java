package com.yunusemrecelik.twitchurlextractiontool.model;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAccessTokenRequestModel {
    private String operationName;
    private Map<String, Map<String, Object>> extensions;
    private Map<String, Object> variables;
}
