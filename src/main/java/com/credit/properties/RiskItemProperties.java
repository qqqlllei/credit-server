package com.credit.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "risk.item")
@PropertySource("classpath:risk.properties")
@Component
public class RiskItemProperties {


   private List<String> judicialRecordList = new ArrayList<>();

   private List<String> identityRecordList = new ArrayList<>();

   private List<String> loanRecordList = new ArrayList<>();


   private List<String> namelistRecordList = new ArrayList<>();



    public List<String> getJudicialRecordList() {
        return judicialRecordList;
    }

    public void setJudicialRecordList(List<String> judicialRecordList) {
        this.judicialRecordList = judicialRecordList;
    }

    public List<String> getIdentityRecordList() {
        return identityRecordList;
    }

    public void setIdentityRecordList(List<String> identityRecordList) {
        this.identityRecordList = identityRecordList;
    }

    public List<String> getLoanRecordList() {
        return loanRecordList;
    }

    public void setLoanRecordList(List<String> loanRecordList) {
        this.loanRecordList = loanRecordList;
    }

    public List<String> getNamelistRecordList() {
        return namelistRecordList;
    }

    public void setNamelistRecordList(List<String> namelistRecordList) {
        this.namelistRecordList = namelistRecordList;
    }
}
