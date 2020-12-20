package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.StatusTimeOffEntity;
import com.ces.intern.apitimecloud.entity.TimeOffEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeOffRepository extends JpaRepository<TimeOffEntity, Integer> {
    List<TimeOffEntity> getAllTimeOffByUserId(Integer userId);

    @Query(value="select count(*) from status_time_off where status = 2 and time_off_id in (select time_off_id from time_off where user_id = :userId)", nativeQuery = true)
    int countApprovedTimeOffByUserId(@Param("userId") Integer userId);

}
