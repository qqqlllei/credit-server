package com.credit.entity;
import com.credit.base.BaseEntity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/10/20 0020.
 */
public class CreditRequest extends BaseEntity{

    private String phone;
    private String name;
    private String idcard;
    private String info;
    private String comeFrom;
    private Timestamp createTime;
    private Timestamp updateTime;

    private String status;

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
    public String getIdcard(){
        return idcard;
    }
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public Timestamp getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    public Timestamp getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
