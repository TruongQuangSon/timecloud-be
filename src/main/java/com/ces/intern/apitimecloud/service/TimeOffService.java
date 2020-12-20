package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.StatusTimeOffDTO;
import com.ces.intern.apitimecloud.dto.TimeOffDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.TimeOffEntity;
import com.ces.intern.apitimecloud.http.request.TimeOffRequest;

import java.util.List;

public interface TimeOffService {
    TimeOffDTO getTimeOffById(Integer timeOffId);
    TimeOffEntity createTimeOff(TimeOffRequest timeOffRequest);
    void deleteTimeOff(Integer timeOffId);
    TimeOffDTO updateTimeOff(TimeOffRequest timeOffRequest, Integer timeOffId);
    List<StatusTimeOffDTO> getTimeOffByUserId(Integer userId);
    Integer countTimeOffApprovedByUserId(Integer userId);
    List<UserDTO> getApprover();
    List<StatusTimeOffDTO> getAllTimeOffsByStatusId(Integer statusId);

}
