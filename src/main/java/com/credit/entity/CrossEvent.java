package com.credit.entity;
import com.credit.base.BaseEntity;

public class CrossEvent extends BaseEntity{

	private String type;
	private String crossFrequencyDetailList;
	private String data;
	private String detail;



	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	public void setCrossFrequencyDetailList(String crossFrequencyDetailList) {
		this.crossFrequencyDetailList = crossFrequencyDetailList;
	}
	public String getCrossFrequencyDetailList() {
		return this.crossFrequencyDetailList;
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
}

