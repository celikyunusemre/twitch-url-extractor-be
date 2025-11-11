package com.yunusemrecelik.twitchurlextractiontool.model.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamPlaybackAccessToken {
    private String value;
    private String signature;
    private Authorization authorization;
}
