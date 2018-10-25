package com.credit.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.credit.base.BaseService;
import com.credit.entity.CreditRequest;
import com.credit.entity.IdentityRecord;
import com.credit.entity.JudicialRecord;
import com.credit.entity.OverdueRecord;

import java.util.List;

/**
 * Created by 李雷 on 2018/10/24.
 */
public interface CreditRequestService extends BaseService<CreditRequest> {

    int saveCreditRequest(CreditRequest creditRequest);

    CreditRequest getCreditRequestByPhone(String phone);

    List<CreditRequest> getCreditRequestByStatus(String unDo);

    List<JudicialRecord> courtcInfoHandle(JSONArray list,CreditRequest creditRequest);

    void saveCreditInfo(JSONArray jsonArray,CreditRequest creditRequest);

    List<OverdueRecord> OverdueRecordHandle(JSONArray jsonArray, CreditRequest creditRequest);

    List<IdentityRecord> identityRecordHandle(JSONArray jsonArray, CreditRequest creditRequest);
}
