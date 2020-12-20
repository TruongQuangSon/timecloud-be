package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.StatusTimeOffEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusTimeOffRepository extends JpaRepository<StatusTimeOffEntity,Integer > {
    List<StatusTimeOffEntity> findAll();
    List<StatusTimeOffEntity> findAllByStatus(Integer status);

    List<StatusTimeOffEntity> getAllByStatusNotIn(List<Integer> statusList, Pageable p);
}
