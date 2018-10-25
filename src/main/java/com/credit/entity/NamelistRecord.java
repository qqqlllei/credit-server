package com.credit.entity;
import com.credit.base.BaseEntity;

public class NamelistRecord extends BaseEntity{

	private String type;
	private String description;
	private String hitTypeDisplayName;
	private String fraudTypeDisplayName;
	private String greyListDetails;
	private String value;
	private String riskLevel;
	private String fraudType;
	private String evidenceTime;



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
	public void setHitTypeDisplayName(String hitTypeDisplayName) {
		this.hitTypeDisplayName = hitTypeDisplayName;
	}
	public String getHitTypeDisplayName() {
		return this.hitTypeDisplayName;
	}
	public void setFraudTypeDisplayName(String fraudTypeDisplayName) {
		this.fraudTypeDisplayName = fraudTypeDisplayName;
	}
	public String getFraudTypeDisplayName() {
		return this.fraudTypeDisplayName;
	}
	public void setGreyListDetails(String greyListDetails) {
		this.greyListDetails = greyListDetails;
	}
	public String getGreyListDetails() {
		return this.greyListDetails;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return this.value;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getRiskLevel() {
		return this.riskLevel;
	}
	public void setFraudType(String fraudType) {
		this.fraudType = fraudType;
	}
	public String getFraudType() {
		return this.fraudType;
	}
	public void setEvidenceTime(String evidenceTime) {
		this.evidenceTime = evidenceTime;
	}
	public String getEvidenceTime() {
		return this.evidenceTime;
	}
}

