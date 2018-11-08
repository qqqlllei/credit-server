package com.credit.entity;
import com.alibaba.fastjson.JSONArray;
import com.credit.base.BaseEntity;

public class OverdueRecord extends BaseEntity{

	private String type;
	private String description;
	private String discreditTimes;
	private String overdueDetails;
	private String overdueAmountRange;
	private String overdueCount;
	private String overdueDayRange;
	private String overdueTime;

	private JSONArray datas;



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
	public void setDiscreditTimes(String discreditTimes) {
		this.discreditTimes = discreditTimes;
	}
	public String getDiscreditTimes() {
		return this.discreditTimes;
	}
	public void setOverdueDetails(String overdueDetails) {
		this.overdueDetails = overdueDetails;
	}
	public String getOverdueDetails() {
		return this.overdueDetails;
	}
	public void setOverdueAmountRange(String overdueAmountRange) {
		this.overdueAmountRange = overdueAmountRange;
	}
	public String getOverdueAmountRange() {
		return this.overdueAmountRange;
	}
	public void setOverdueCount(String overdueCount) {
		this.overdueCount = overdueCount;
	}
	public String getOverdueCount() {
		return this.overdueCount;
	}
	public void setOverdueDayRange(String overdueDayRange) {
		this.overdueDayRange = overdueDayRange;
	}
	public String getOverdueDayRange() {
		return this.overdueDayRange;
	}
	public void setOverdueTime(String overdueTime) {
		this.overdueTime = overdueTime;
	}
	public String getOverdueTime() {
		return this.overdueTime;
	}

	public JSONArray getDatas() {
		return datas;
	}

	public void setDatas(JSONArray datas) {
		this.datas = datas;
	}
}

