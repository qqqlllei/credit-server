package com.credit.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.credit.base.BaseService;
import com.credit.entity.*;

import java.util.List;

/**
 * Created by 李雷 on 2018/10/24.
 */
public interface CreditRequestService extends BaseService<CreditRequest> {

    int saveCreditRequest(CreditRequest creditRequest);

    CreditRequest getCreditRequestByPhone(String phone);

    List<CreditRequest> getCreditRequestByStatus(String unDo);

    List<JudicialRecord> courtcInfoHandle(JSONArray list,CreditRequest creditRequest,String reportId);

    void saveCreditInfo(JSONArray jsonArray,CreditRequest creditRequest,String reportId);

    List<OverdueRecord> OverdueRecordHandle(JSONArray jsonArray, CreditRequest creditRequest);

    List<IdentityRecord> identityRecordHandle(JSONArray jsonArray, CreditRequest creditRequest,String reportId);

    List<LoanRecord> loanRecordHandle(JSONArray jsonArray, CreditRequest creditRequest,String reportId);

    List<NamelistRecord> namelistRecordHandle(JSONArray jsonArray, CreditRequest creditRequest,String reportId);
}
