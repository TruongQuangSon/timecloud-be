package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.StatusTimeOffDTO;
import com.ces.intern.apitimecloud.entity.StatusTimeOffEntity;
import com.ces.intern.apitimecloud.http.request.StatusTimeOffRequest;
import com.ces.intern.apitimecloud.service.StatusTimeOffService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status-time-off")
public class StatusTimeOffController {

    private final StatusTimeOffService statusTimeOffService;
    private final ModelMapper modelMapper;

    public StatusTimeOffController(StatusTimeOffService statusTimeOffService,
                                   ModelMapper modelMapper){
        this.statusTimeOffService = statusTimeOffService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/non_pending")
    public List<StatusTimeOffDTO> getAll(@RequestParam(value = "limit")Integer limit,
                                         @RequestParam(value = "page")Integer page){
        return statusTimeOffService.getAllStatusTimeOffNonPending(limit, page);
    }

    @GetMapping("/{time-off-id}")
    public StatusTimeOffDTO getById(@PathVariable(value = "time-off-id")Integer timeOffId){
        StatusTimeOffEntity statusTimeOffEntity = statusTimeOffService.getById(timeOffId);
        return modelMapper.map(statusTimeOffEntity, StatusTimeOffDTO.class);
    }

    @PutMapping("/{time-off-id}")
    public StatusTimeOffDTO update(@PathVariable(value="time-off-id")Integer timeOffId,
                                   @RequestBody StatusTimeOffRequest request){
        StatusTimeOffEntity statusTimeOffEntity = statusTimeOffService.update(request, timeOffId);
        return modelMapper.map(statusTimeOffEntity, StatusTimeOffDTO.class);
    }




}
