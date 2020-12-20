package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.StatusTimeOffDTO;
import com.ces.intern.apitimecloud.entity.StatusTimeOffEntity;
import com.ces.intern.apitimecloud.entity.TimeOffEntity;
import com.ces.intern.apitimecloud.http.request.StatusTimeOffRequest;

import java.util.List;

public interface StatusTimeOffService {

    StatusTimeOffEntity getById(Integer timeOffId);
    StatusTimeOffEntity create(TimeOffEntity timeOffEntity);
    StatusTimeOffEntity update(StatusTimeOffRequest request, Integer timeOffId);
    void delete(Integer timeOffId);
    List<StatusTimeOffDTO> getAllStatusTimeOff();
    List<StatusTimeOffDTO> getAllStatusTimeOffNonPending(Integer limit, Integer page);

}
