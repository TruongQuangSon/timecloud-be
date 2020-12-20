package com.ces.intern.apitimecloud.service.impl;


import com.ces.intern.apitimecloud.dto.StatusTimeOffDTO;
import com.ces.intern.apitimecloud.entity.StatusTimeOffEntity;
import com.ces.intern.apitimecloud.entity.TimeOffEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.http.request.StatusTimeOffRequest;
import com.ces.intern.apitimecloud.repository.StatusTimeOffRepository;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.service.StatusTimeOffService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.StatusTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusTimeOffServiceImpl implements StatusTimeOffService {

    private final StatusTimeOffRepository statusTimeOffRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public StatusTimeOffServiceImpl(StatusTimeOffRepository statusTimeOffRepository,
                                    UserRepository userRepository,
                                    ModelMapper modelMapper){
        this.statusTimeOffRepository = statusTimeOffRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public StatusTimeOffEntity getById(Integer timeOffId) {
        StatusTimeOffEntity statusTimeOffEntity = statusTimeOffRepository
                .findById(timeOffId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with timeoffId " + timeOffId));

        return statusTimeOffEntity;
    }

    @Override
    public StatusTimeOffEntity create(TimeOffEntity timeOffEntity) {

        StatusTimeOffEntity statusTimeOffEntity = new StatusTimeOffEntity();
        statusTimeOffEntity.setTimeOff(timeOffEntity);
        statusTimeOffEntity.setStatus(StatusTO.PENDING.getId());

        return statusTimeOffRepository.save(statusTimeOffEntity);
    }

    @Override
    public StatusTimeOffEntity update(StatusTimeOffRequest request, Integer timeOffId) {

        StatusTimeOffEntity statusTimeOffEntity = getById(timeOffId);
        UserEntity userEntity = userRepository.findById(request.getApproverId())
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()));

        statusTimeOffEntity.setStatus(request.getStatus());
        statusTimeOffEntity.setApprover(userEntity);
        statusTimeOffEntity.setAcceptAt(new Date());
        statusTimeOffEntity.setResponse(request.getResponse());

        return statusTimeOffRepository.save(statusTimeOffEntity);
    }

    @Override
    public void delete(Integer timeOffId) {
        statusTimeOffRepository.deleteById(timeOffId);
    }

    @Override
    public List<StatusTimeOffDTO> getAllStatusTimeOff() {
        List<StatusTimeOffEntity> statusTimeOffEntities = statusTimeOffRepository.findAll();
        return statusTimeOffEntities
                .stream()
                .map(statusTimeOffEntity -> modelMapper.map(statusTimeOffEntity, StatusTimeOffDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StatusTimeOffDTO> getAllStatusTimeOffNonPending(Integer limit, Integer page) {
        Pageable p = PageRequest.of(page, limit);
        List<Integer> temp = Arrays.asList(StatusTO.PENDING.getId());
        List<StatusTimeOffEntity> statusTimeOffEntities = statusTimeOffRepository.getAllByStatusNotIn(temp, p);
        return statusTimeOffEntities
                .stream()
                .map(ele -> modelMapper.map(ele, StatusTimeOffDTO.class))
                .collect(Collectors.toList());
    }

}
