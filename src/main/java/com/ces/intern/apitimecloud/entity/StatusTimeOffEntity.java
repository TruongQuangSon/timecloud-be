package com.ces.intern.apitimecloud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name ="status_time_off", schema ="public")
public class StatusTimeOffEntity implements Serializable {

    private static final long serialVersionUID = 1263712226577302618L;
    @Id
    @Column(name = "time_off_id")
    private Integer id;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "accepted_by")
    private UserEntity approver;

    @Column(name ="accept_at")
    private Date acceptAt;

    @Column(name = "response")
    private String response;

    @Column(name = "status")
    private Integer status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_off_id")
    @MapsId
    private TimeOffEntity timeOff;
}
