package com.ces.intern.apitimecloud.util;

public enum  StatusTO {
    PENDING(1),
    APPROVE(2),
    REJECT(3);

    private Integer id;
    StatusTO(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
