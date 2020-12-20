package com.ces.intern.apitimecloud.http.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TimeOffRequest {
    private Integer userId;
    private Long milisecondsStartTime;
    private Long milisecondsEndTime;
    private String description;
}
