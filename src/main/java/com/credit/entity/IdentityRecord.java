package com.credit.entity;
import com.alibaba.fastjson.JSONArray;
import com.credit.base.BaseEntity;

public class IdentityRecord extends BaseEntity{

	private String type;
	private String frequencyDetailList;
	private String data;
	private String detail;

	private JSONArray datas;



	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	public void setFrequencyDetailList(String frequencyDetailList) {
		this.frequencyDetailList = frequencyDetailList;
	}
	public String getFrequencyDetailList() {
		return this.frequencyDetailList;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getData() {
		return this.data;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getDetail() {
		return this.detail;
	}

	public JSONArray getDatas() {
		return datas;
	}

	public void setDatas(JSONArray datas) {
		this.datas = datas;
	}
}

