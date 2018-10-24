package com.credit.base;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 基类
 */
public class BaseEntity implements Serializable{

	private String id;// 主键ID.
	private Integer version ;// 版本号默认为0
	private String remark;// 描述


	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
