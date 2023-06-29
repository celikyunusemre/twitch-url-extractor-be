package com.yunusemrecelik.twitchurlextractiontool.model;

import lombok.*;

import java.util.HashMap;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAccessTokenResponseModel {
    private HashMap<String, HashMap<String, String>> data;
    private HashMap<String, String> extensions;
}
