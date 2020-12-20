package com.ces.intern.apitimecloud.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class StatusTimeOffDTO {
    private Integer id;
    private TimeOffDTO timeOff;
    private UserDTO approver;
    private String response;
    private Integer status;
    private Date acceptAt;
}
