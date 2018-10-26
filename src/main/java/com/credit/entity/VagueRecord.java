package com.credit.entity;
import com.credit.base.BaseEntity;

public class VagueRecord extends BaseEntity{

	private String type;
	private String description;
	private String fraudTypeDisplayName;
	private String fuzzyListDetails;
	private String fuzzyIdNumber;
	private String fuzzyName;
	private String fraudType;



	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return this.description;
	}
	public void setFraudTypeDisplayName(String fraudTypeDisplayName) {
		this.fraudTypeDisplayName = fraudTypeDisplayName;
	}
	public String getFraudTypeDisplayName() {
		return this.fraudTypeDisplayName;
	}
	public void setFuzzyListDetails(String fuzzyListDetails) {
		this.fuzzyListDetails = fuzzyListDetails;
	}
	public String getFuzzyListDetails() {
		return this.fuzzyListDetails;
	}
	public void setFuzzyIdNumber(String fuzzyIdNumber) {
		this.fuzzyIdNumber = fuzzyIdNumber;
	}
	public String getFuzzyIdNumber() {
		return this.fuzzyIdNumber;
	}
	public void setFuzzyName(String fuzzyName) {
		this.fuzzyName = fuzzyName;
	}
	public String getFuzzyName() {
		return this.fuzzyName;
	}
	public void setFraudType(String fraudType) {
		this.fraudType = fraudType;
	}
	public String getFraudType() {
		return this.fraudType;
	}
}

