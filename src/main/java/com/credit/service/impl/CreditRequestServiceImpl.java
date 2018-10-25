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


    private static final String DEFAULT_VALUE="-";

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
    public List<JudicialRecord> courtcInfoHandle(JSONArray list,CreditRequest creditRequest) {

        Iterator<Object> it =  list.iterator();
        List<JudicialRecord> courtcs = new ArrayList<>();
        while (it.hasNext()){
            JSONObject item = (JSONObject) it.next();
            if("身份证命中法院失信名单".equals(item.getString("item_name"))||"身份证命中法院执行名单".equals(item.getString("item_name"))||"身份证命中法院结案名单".equals(item.getString("item_name"))){
                JSONArray namelist_hit_details = item.getJSONObject("item_detail").getJSONArray("namelist_hit_details");

                Iterator<Object> namelist_hit_details_it = namelist_hit_details.iterator();
                while (namelist_hit_details_it.hasNext()){
                    JSONObject namelist_hit_details_item = (JSONObject)namelist_hit_details_it.next();

                    String hit_type_displayname = namelist_hit_details_item.getString("hit_type_displayname");

                    JSONArray court_details =  namelist_hit_details_item.getJSONArray("court_details");

                    Iterator<Object> court_details_it = court_details.iterator();

                    while (court_details_it.hasNext()){
                        JSONObject court_details_item = (JSONObject) court_details_it.next();



                        JudicialRecord judicialRecord = new JudicialRecord();
                        judicialRecord.setAge((String) court_details_item.getOrDefault("age",DEFAULT_VALUE));
                        judicialRecord.setEvidenceCourt((String) court_details_item.getOrDefault("court_name",DEFAULT_VALUE));
                        judicialRecord.setExecuteCourt((String) court_details_item.getOrDefault("court_name",DEFAULT_VALUE));
                        judicialRecord.setProvince((String) court_details_item.getOrDefault("province",DEFAULT_VALUE));
                        judicialRecord.setCarryOut((String) court_details_item.getOrDefault("situation",DEFAULT_VALUE));
                        judicialRecord.setCaseDate((String) court_details_item.getOrDefault("filing_time",DEFAULT_VALUE));
                        judicialRecord.setGender((String) court_details_item.getOrDefault("gender",DEFAULT_VALUE));
                        judicialRecord.setFraudType((String) court_details_item.getOrDefault("fraud_type",DEFAULT_VALUE));
                        judicialRecord.setExecutedName((String) court_details_item.getOrDefault("name",DEFAULT_VALUE));
                        judicialRecord.setTermDuty((String) court_details_item.getOrDefault("duty",DEFAULT_VALUE));
                        judicialRecord.setSpecificCircumstances((String) court_details_item.getOrDefault("discredit_detail",DEFAULT_VALUE));


                        if(hit_type_displayname.contains("身份证")){
                            judicialRecord.setValue(creditRequest.getIdcard());
                        }else{
                            judicialRecord.setValue(creditRequest.getPhone());
                        }



                        judicialRecord.setCaseCode((String) court_details_item.getOrDefault("case_number",DEFAULT_VALUE));
                        courtcs.add(judicialRecord);
                    }
                }
            }
        }

        return courtcs;
    }

    @Override
    public void saveCreditInfo(JSONArray jsonArray,CreditRequest creditRequest) {


        //1
        List<JudicialRecord> courtInfoList = courtcInfoHandle(jsonArray,creditRequest);

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
