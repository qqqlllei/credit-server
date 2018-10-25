package com.credit.entity;
import com.credit.base.BaseEntity;

public class JudicialRecord extends BaseEntity{

	private String caseCode;
	private String executeCourt;
	private java.sql.Timestamp caseDate;
	private String gender;
	private String carryOut;
	private String executedName;
	private String fraudType;
	private String specificCircumstances;
	private String executeSubject;
	private String executeStatus;
	private String evidenceCourt;
	private String termDuty;
	private String province;
	private String executeCode;
	private String value;
	private String age;
	private java.sql.Timestamp evidenceTime;
	private String fraudTypeDisplayName;



	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getCaseCode() {
		return this.caseCode;
	}
	public void setExecuteCourt(String executeCourt) {
		this.executeCourt = executeCourt;
	}
	public String getExecuteCourt() {
		return this.executeCourt;
	}
	public void setCaseDate(java.sql.Timestamp caseDate) {
		this.caseDate = caseDate;
	}
	public java.sql.Timestamp getCaseDate() {
		return this.caseDate;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getGender() {
		return this.gender;
	}
	public void setCarryOut(String carryOut) {
		this.carryOut = carryOut;
	}
	public String getCarryOut() {
		return this.carryOut;
	}
	public void setExecutedName(String executedName) {
		this.executedName = executedName;
	}
	public String getExecutedName() {
		return this.executedName;
	}
	public void setFraudType(String fraudType) {
		this.fraudType = fraudType;
	}
	public String getFraudType() {
		return this.fraudType;
	}
	public void setSpecificCircumstances(String specificCircumstances) {
		this.specificCircumstances = specificCircumstances;
	}
	public String getSpecificCircumstances() {
		return this.specificCircumstances;
	}
	public void setExecuteSubject(String executeSubject) {
		this.executeSubject = executeSubject;
	}
	public String getExecuteSubject() {
		return this.executeSubject;
	}
	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}
	public String getExecuteStatus() {
		return this.executeStatus;
	}
	public void setEvidenceCourt(String evidenceCourt) {
		this.evidenceCourt = evidenceCourt;
	}
	public String getEvidenceCourt() {
		return this.evidenceCourt;
	}
	public void setTermDuty(String termDuty) {
		this.termDuty = termDuty;
	}
	public String getTermDuty() {
		return this.termDuty;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvince() {
		return this.province;
	}
	public void setExecuteCode(String executeCode) {
		this.executeCode = executeCode;
	}
	public String getExecuteCode() {
		return this.executeCode;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return this.value;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAge() {
		return this.age;
	}
	public void setEvidenceTime(java.sql.Timestamp evidenceTime) {
		this.evidenceTime = evidenceTime;
	}
	public java.sql.Timestamp getEvidenceTime() {
		return this.evidenceTime;
	}
	public void setFraudTypeDisplayName(String fraudTypeDisplayName) {
		this.fraudTypeDisplayName = fraudTypeDisplayName;
	}
	public String getFraudTypeDisplayName() {
		return this.fraudTypeDisplayName;
	}
}

