package com.ces.intern.apitimecloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TimeOffDTO extends BaseDTO {
    private UserDTO user;
    private Date startTime;
    private Date endTime;
    private String Description;
}
