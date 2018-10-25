package com.credit.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.credit.base.BaseDao;
import com.credit.base.BaseServiceImpl;
import com.credit.dao.CreditRequestMapper;
import com.credit.entity.CreditRequest;
import com.credit.entity.JudicialRecord;
import com.credit.service.CreditRequestService;
import com.credit.service.JudicialRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2018/10/20 0020.
 */
@Service("creditRequestService")
public class CreditRequestServiceImpl extends BaseServiceImpl<CreditRequest> implements CreditRequestService {

    @Autowired
    private CreditRequestMapper creditRequestMapper;

    @Autowired
    private JudicialRecordService judicialRecordService;

    public int saveCreditRequest(CreditRequest creditRequest){
        creditRequest.setId(UUID.randomUUID().toString());
        return creditRequestMapper.insert(creditRequest);
    }

    public CreditRequest getCreditRequestByPhone(String phone) {
        return creditRequestMapper.getCreditRequestByPhone(phone);
    }

    @Override
    public List<CreditRequest> getCreditRequestByStatus(String unDo) {
        return creditRequestMapper.getCreditRequestByStatus(unDo);
    }

    @Override
    public List<JudicialRecord> courtcInfoHandle(JSONArray list) {

        Iterator<Object> it =  list.iterator();
        List<JudicialRecord> courtcs = new ArrayList<>();
        while (it.hasNext()){
            JSONObject item = (JSONObject) it.next();
            if("身份证命中法院失信名单".equals(item.getString("item_name"))||"身份证命中法院执行名单".equals(item.getString("item_name"))||"身份证命中法院结案名单".equals(item.getString("item_name"))){
                JSONArray namelist_hit_details = item.getJSONObject("item_detail").getJSONArray("namelist_hit_details");

                Iterator<Object> namelist_hit_details_it = namelist_hit_details.iterator();
                while (namelist_hit_details_it.hasNext()){
                    JSONObject namelist_hit_details_item = (JSONObject)namelist_hit_details_it.next();

                    JSONArray court_details =  namelist_hit_details_item.getJSONArray("court_details");

                    Iterator<Object> court_details_it = court_details.iterator();

                    while (court_details_it.hasNext()){
                        JSONObject court_details_item = (JSONObject) court_details_it.next();

                        JudicialRecord judicialRecord = new JudicialRecord();
                        judicialRecord.setAge(court_details_item.getString("age"));
                        judicialRecord.setEvidenceCourt(court_details_item.getString("court_name"));
                        judicialRecord.setExecuteCourt(court_details_item.getString("court_name"));
                        judicialRecord.setProvince(court_details_item.getString("province"));
                        judicialRecord.setCarryOut(court_details_item.getString("situation"));
                        judicialRecord.setCaseDate(court_details_item.getString("filing_time"));
                        judicialRecord.setGender(court_details_item.getString("gender"));
                        judicialRecord.setFraudType(court_details_item.getString("fraud_type"));
                        judicialRecord.setExecutedName(court_details_item.getString("name"));
                        judicialRecord.setTermDuty(court_details_item.getString("duty"));
                        judicialRecord.setSpecificCircumstances(court_details_item.getString("discredit_detail"));

                        judicialRecord.setCaseCode(court_details_item.getString("case_number"));
                        courtcs.add(judicialRecord);
                    }
                }


            }

//            if("身份证命中法院执行名单".equals(item.getString("item_name"))){
//                courtcs.add(item);
//            }
//
//            if("身份证命中法院结案名单".equals(item.getString("item_name"))){
//                courtcs.add(item);
//            }

        }

        return courtcs;
    }

    @Override
    public void saveCreditInfo(JSONArray jsonArray) {


        //1
        List<JudicialRecord> courtInfoList = courtcInfoHandle(jsonArray);

        for (JudicialRecord judicialRecord: courtInfoList){
            judicialRecord.setId(UUID.randomUUID().toString());
            judicialRecordService.save(judicialRecord);
        }

    }

    @Override
    protected BaseDao getBaseDao() {
        return creditRequestMapper;
    }

}
