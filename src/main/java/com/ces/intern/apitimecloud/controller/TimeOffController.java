package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.StatusTimeOffDTO;
import com.ces.intern.apitimecloud.dto.TimeOffDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.TimeOffEntity;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.TimeOffRequest;
import com.ces.intern.apitimecloud.repository.TimeOffRepository;
import com.ces.intern.apitimecloud.service.TimeOffService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import com.ces.intern.apitimecloud.util.StatusTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-off")
public class TimeOffController {

    private final TimeOffService timeOffService;
    private final ModelMapper modelMapper;

    public TimeOffController(TimeOffService timeOffService,
                      ModelMapper modelMapper
                        ){
        this.timeOffService = timeOffService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{timeOffId}")
    public TimeOffDTO get(@PathVariable(value = "timeOffId")Integer timeOffId){
        return timeOffService.getTimeOffById(timeOffId);
    }
    @PostMapping("")
    public TimeOffDTO create(@RequestBody TimeOffRequest timeOffRequest) throws Exception {
        if(timeOffRequest.getDescription().isEmpty()) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.name());
        TimeOffEntity timeOffEntity = timeOffService.createTimeOff(timeOffRequest);
        return modelMapper.map(timeOffEntity, TimeOffDTO.class);
    }

    @PutMapping("/{timeOffId}")
    private TimeOffDTO update(@RequestBody TimeOffRequest timeOffRequest, @PathVariable Integer timeOffId) throws Exception {
        return timeOffService.updateTimeOff(timeOffRequest, timeOffId);
    }

    @DeleteMapping("/{timeOffId}")
    private String delete(@PathVariable Integer timeOffId) {
        timeOffService.deleteTimeOff(timeOffId);
        return ResponseMessage.DELETE_SUCCESS;
    }

    @GetMapping("/approver")
    public List<UserDTO> getApproved() {
        return timeOffService.getApprover();
    }

    @GetMapping("/pending")
    public List<StatusTimeOffDTO> getAllPendingTimeOff() {
        return timeOffService.getAllTimeOffsByStatusId(StatusTO.PENDING.getId());
    }


    @GetMapping("/approve")
    public List<StatusTimeOffDTO> getAllApproveTimeOff(){
        return timeOffService.getAllTimeOffsByStatusId(StatusTO.APPROVE.getId());
    }

}
