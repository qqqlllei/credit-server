package com.credit.entity;
import com.credit.base.BaseEntity;

public class LoanRecord extends BaseEntity{

	private String type;
	private String description;
	private String platformCount;
	private String platformDetailDimension;
	private String platformDetail;
	private String count;
	private String detail;
	private String dimension;
	private String industryDisplayName;



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
	public void setPlatformCount(String platformCount) {
		this.platformCount = platformCount;
	}
	public String getPlatformCount() {
		return this.platformCount;
	}
	public void setPlatformDetailDimension(String platformDetailDimension) {
		this.platformDetailDimension = platformDetailDimension;
	}
	public String getPlatformDetailDimension() {
		return this.platformDetailDimension;
	}
	public void setPlatformDetail(String platformDetail) {
		this.platformDetail = platformDetail;
	}
	public String getPlatformDetail() {
		return this.platformDetail;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getCount() {
		return this.count;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getDetail() {
		return this.detail;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getDimension() {
		return this.dimension;
	}
	public void setIndustryDisplayName(String industryDisplayName) {
		this.industryDisplayName = industryDisplayName;
	}
	public String getIndustryDisplayName() {
		return this.industryDisplayName;
	}
}

