package com.credit.entity;

import com.credit.base.BaseEntity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/12/23 0023.
 */
public class LogInfo extends BaseEntity {

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    private String phone;
    private String name;
    private String idcard;
    private Timestamp createTime;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
