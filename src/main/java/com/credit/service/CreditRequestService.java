package com.credit.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.credit.base.BaseService;
import com.credit.entity.CreditRequest;
import com.credit.entity.JudicialRecord;

import java.util.List;

/**
 * Created by 李雷 on 2018/10/24.
 */
public interface CreditRequestService extends BaseService<CreditRequest> {

    int saveCreditRequest(CreditRequest creditRequest);

    CreditRequest getCreditRequestByPhone(String phone);

    List<CreditRequest> getCreditRequestByStatus(String unDo);

    List<JudicialRecord> courtcInfoHandle(JSONArray list);

    void saveCreditInfo(JSONArray jsonArray);
}
