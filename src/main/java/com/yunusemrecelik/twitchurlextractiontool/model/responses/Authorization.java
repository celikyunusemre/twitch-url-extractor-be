package com.yunusemrecelik.twitchurlextractiontool.model.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Authorization {
    private Boolean isForbidden;
    private String reason;
    private String forbiddenReasonCode;
}
