package com.ces.intern.apitimecloud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="time_off", schema = "public")
@AttributeOverride(name = "id", column = @Column(name = "time_off_id"))
@SequenceGenerator(name = "generator", sequenceName = "time_off_id_seq", allocationSize = 1, schema = "public")
public class TimeOffEntity extends BaseEntity{

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name="start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name= "description")
    private String description;


}
