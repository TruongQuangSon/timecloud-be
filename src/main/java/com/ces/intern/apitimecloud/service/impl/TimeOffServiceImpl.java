package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.StatusTimeOffDTO;
import com.ces.intern.apitimecloud.dto.TimeOffDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.StatusTimeOffEntity;
import com.ces.intern.apitimecloud.entity.TimeOffEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.entity.UserRoleEntity;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.http.request.TimeOffRequest;
import com.ces.intern.apitimecloud.repository.ProjectUserRepository;
import com.ces.intern.apitimecloud.repository.StatusTimeOffRepository;
import com.ces.intern.apitimecloud.repository.TimeOffRepository;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.service.StatusTimeOffService;
import com.ces.intern.apitimecloud.service.TimeOffService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.StatusTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeOffServiceImpl implements TimeOffService {

    private final TimeOffRepository timeOffRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final StatusTimeOffService statusTimeOffService;
    private final StatusTimeOffRepository statusTimeOffRepository;


    public TimeOffServiceImpl(TimeOffRepository timeOffRepository,
                              UserRepository userRepository,
                              ModelMapper modelMapper,
                              StatusTimeOffService statusTimeOffService,
                              StatusTimeOffRepository statusTimeOffRepository
                            ){
        this.userRepository = userRepository;
        this.timeOffRepository = timeOffRepository;
        this.modelMapper= modelMapper;
        this.statusTimeOffService = statusTimeOffService;
        this.statusTimeOffRepository = statusTimeOffRepository;
    }

    @Override
    public TimeOffDTO getTimeOffById(Integer timeOffId) {
        TimeOffEntity timeOffEntity = timeOffRepository
                .findById(timeOffId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with id : " + timeOffId));
        return modelMapper.map(timeOffEntity, TimeOffDTO.class);
    }

    @Override
    @Transactional
    public TimeOffEntity createTimeOff(TimeOffRequest timeOffRequest) {
        UserEntity userEntity = userRepository.findById(timeOffRequest.getUserId())
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + "with" + timeOffRequest.getUserId()));
        TimeOffEntity timeOffEntity = modelMapper.map(timeOffRequest, TimeOffEntity.class);
        timeOffEntity.setStartTime(new Date(timeOffRequest.getMilisecondsStartTime()));
        timeOffEntity.setEndTime(new Date(timeOffRequest.getMilisecondsEndTime()));
        timeOffEntity.setUser(userEntity);
        timeOffEntity.setBasicInfo(new Date(), timeOffRequest.getUserId(), new Date(), timeOffRequest.getUserId());

        statusTimeOffService.create(timeOffRepository.save(timeOffEntity));

        return timeOffEntity;
    }

    @Override
    public void deleteTimeOff(Integer timeOffId) {
        statusTimeOffService.delete(timeOffId);
    }

    @Override
    public TimeOffDTO updateTimeOff(TimeOffRequest timeOffRequest, Integer timeOffId) {
        TimeOffEntity timeOffEntity = timeOffRepository.findById(timeOffId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + "with" + timeOffId));
        timeOffEntity.setStartTime(new Date(timeOffRequest.getMilisecondsStartTime()));
        timeOffEntity.setEndTime(new Date(timeOffRequest.getMilisecondsEndTime()));
        timeOffEntity.setDescription(timeOffRequest.getDescription());
        timeOffEntity.setModifyAt(new Date());
        TimeOffEntity timeOffEntityUpdate = timeOffRepository.save(timeOffEntity);
        TimeOffDTO timeOffDTO = modelMapper.map(timeOffEntityUpdate, TimeOffDTO.class);
        timeOffDTO.setUser(modelMapper.map(timeOffEntityUpdate.getUser(), UserDTO.class));
        return timeOffDTO;
    }

    @Override
    public List<StatusTimeOffDTO> getTimeOffByUserId(Integer userId) {
        List<TimeOffEntity> timeOffs = timeOffRepository.getAllTimeOffByUserId(userId);
        List<StatusTimeOffEntity> statustimeOffEntitys = timeOffs.stream()
                .map(timeOff -> statusTimeOffService.getById(timeOff.getId()))
                .collect(Collectors.toList());
        return statustimeOffEntitys.stream()
                .map(statustimeOffEntity -> modelMapper.map(statustimeOffEntity, StatusTimeOffDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer countTimeOffApprovedByUserId(Integer userId) {
        return timeOffRepository.countApprovedTimeOffByUserId(userId);
    }

    @Override
    public List<UserDTO> getApprover() {
        List<UserEntity> userEntities = userRepository.getApprover();
        return userEntities.stream()
                .map(element -> modelMapper.map(element, UserDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<StatusTimeOffDTO> getAllTimeOffsByStatusId(Integer statusId) {
        List<StatusTimeOffEntity> statusTimeOffEntities =  statusTimeOffRepository.findAllByStatus(statusId);

        return statusTimeOffEntities.stream()
                .map(ele -> modelMapper.map(ele, StatusTimeOffDTO.class))
                .collect(Collectors.toList());
    }


}
