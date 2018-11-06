package com.credit.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.credit.base.BaseDao;
import com.credit.base.BaseServiceImpl;
import com.credit.dao.CreditRequestMapper;
import com.credit.entity.*;
import com.credit.properties.RiskItemProperties;
import com.credit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Autowired
    private IdentityRecordService identityRecordService;

    @Autowired
    private OverdueRecordService overdueRecordService;

    @Autowired
    private LoanRecordService loanRecordService;

    @Autowired
    private NamelistRecordService namelistRecordService;

    @Autowired
    private RiskItemProperties riskItemProperties;

    @Autowired
    private VagueRecordService vagueRecordService;

    @Autowired
    private CrossEventService crossEventService;

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
    public List<JudicialRecord> courtcInfoHandle(JSONArray list,CreditRequest creditRequest,String reportId) {

        Iterator<Object> it =  list.iterator();
        List<JudicialRecord> courtcs = new ArrayList<>();
        while (it.hasNext()){
            JSONObject item = (JSONObject) it.next();
            if(riskItemProperties.getJudicialRecordList().contains(item.getString("item_name"))){
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
                        judicialRecord.setRemark(reportId);

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
    @Transactional
    public void saveCreditInfo(JSONArray jsonArray,CreditRequest creditRequest,String reportId) {
        List<JudicialRecord> courtInfoList = courtcInfoHandle(jsonArray,creditRequest,reportId);

        for (JudicialRecord judicialRecord: courtInfoList){
            judicialRecord.setId(UUID.randomUUID().toString());
            judicialRecordService.save(judicialRecord);
        }

        List<IdentityRecord> identityRecords =  identityRecordHandle(jsonArray,creditRequest,reportId);


        for (IdentityRecord identityRecord: identityRecords){
            identityRecord.setId(UUID.randomUUID().toString());
            identityRecordService.save(identityRecord);
        }
        List<OverdueRecord> overdueRecords =   overdueRecordHandle(jsonArray,creditRequest,reportId);

        for (OverdueRecord overdueRecord: overdueRecords){
            overdueRecord.setId(UUID.randomUUID().toString());
            overdueRecordService.save(overdueRecord);
        }

        List<LoanRecord> loanRecords =   loanRecordHandle(jsonArray,creditRequest,reportId);

        for (LoanRecord loanRecord: loanRecords){
            loanRecord.setId(UUID.randomUUID().toString());
            loanRecordService.save(loanRecord);
        }


        List<NamelistRecord> namelistRecords = namelistRecordHandle(jsonArray,creditRequest,reportId);

        for (NamelistRecord namelistRecord: namelistRecords){
            namelistRecord.setId(UUID.randomUUID().toString());
            namelistRecordService.save(namelistRecord);
        }

        List<VagueRecord> vagueRecords =   vagueRecordHandle(jsonArray,creditRequest,reportId);
        for (VagueRecord vagueRecord: vagueRecords){
            vagueRecord.setId(UUID.randomUUID().toString());
            vagueRecordService.save(vagueRecord);
        }

        List<CrossEvent> crossEventList = crossEventHandle(jsonArray,creditRequest,reportId);
        for (CrossEvent crossEvent: crossEventList){
            crossEvent.setId(UUID.randomUUID().toString());
            crossEventService.save(crossEvent);
        }

        creditRequestMapper.updateStatusToDone(creditRequest.getId());
    }

    private List<CrossEvent> crossEventHandle(JSONArray jsonArray, CreditRequest creditRequest, String reportId) {

        Iterator<Object> it =  jsonArray.iterator();
        List<CrossEvent> crossEventList = new ArrayList<>();



        while (it.hasNext()){
            JSONObject item = (JSONObject) it.next();

            if(riskItemProperties.getCrossEventList().contains(item.getString("item_name"))){
                JSONArray frequency_detail_list = item.getJSONObject("item_detail").getJSONArray("frequency_detail_list");

                Iterator<Object> frequency_detail_list_it = frequency_detail_list.iterator();

                while (frequency_detail_list_it.hasNext()) {
                    JSONObject frequency_detail_list_item = (JSONObject) frequency_detail_list_it.next();

                    CrossEvent crossEvent = new CrossEvent();
                    crossEvent.setType(item.getString("item_name"));
                    crossEvent.setRemark(reportId);

                    crossEvent.setData(frequency_detail_list_item.getJSONArray("data").toJSONString());
                    crossEvent.setDetail((String) frequency_detail_list_item.getOrDefault("detail", DEFAULT_VALUE));

                    crossEventList.add(crossEvent);
                }
            }
        }

        return  crossEventList;
    }

    private List<VagueRecord> vagueRecordHandle(JSONArray jsonArray, CreditRequest creditRequest, String reportId) {

        Iterator<Object> it =  jsonArray.iterator();
        List<VagueRecord> vagurecords = new ArrayList<>();


        while (it.hasNext()){
            JSONObject item = (JSONObject) it.next();

            if(riskItemProperties.getVagueRecordList().contains(item.getString("item_name"))){
                JSONObject item_detail = item.getJSONObject("item_detail");
                JSONArray namelist_hit_details = item_detail.getJSONArray("namelist_hit_details");
                Iterator<Object> namelist_hit_details_it = namelist_hit_details.iterator();
                while (namelist_hit_details_it.hasNext()){
                    JSONObject namelist_hit_details_item = (JSONObject) namelist_hit_details_it.next();
                    //
                    String description = namelist_hit_details_item.getString("description");
                    JSONArray fuzzy_detail_hits = namelist_hit_details_item.getJSONArray("fuzzy_detail_hits");

                    VagueRecord vagueRecord = new VagueRecord();
                    vagueRecord.setRemark(reportId);
                    vagueRecord.setDescription(description);
                    vagueRecord.setType(item.getString("item_name"));
                    vagueRecord.setFuzzyListDetails(fuzzy_detail_hits.toJSONString());
                    vagurecords.add(vagueRecord);
                }

            }
        }

        return  vagurecords;
    }


    //

    @Override
    public List<OverdueRecord> overdueRecordHandle(JSONArray jsonArray, CreditRequest creditRequest,String reportId){
        Iterator<Object> it =  jsonArray.iterator();
        List<OverdueRecord> overdueRecords = new ArrayList<>();
        while (it.hasNext()){
            JSONObject item = (JSONObject) it.next();

            if(("身份证命中信贷逾期名单".equals(item.getString("item_name"))||("手机号命中信贷逾期名单".equals(item.getString("item_name"))))
                   ){
                JSONObject item_detail = item.getJSONObject("item_detail");
                OverdueRecord overdueRecord = new OverdueRecord();

                overdueRecord.setDiscreditTimes(String.valueOf(item_detail.getOrDefault("discredit_times",DEFAULT_VALUE)) );
                JSONArray overdue_details = item_detail.getJSONArray("overdue_details");
                overdueRecord.setRemark(reportId);
                overdueRecord.setDescription(overdue_details.toJSONString());
                overdueRecords.add(overdueRecord);


            }
        }
        return overdueRecords;



    }




    @Override
    public List<IdentityRecord> identityRecordHandle(JSONArray jsonArray, CreditRequest creditRequest,String reportId) {

        Iterator<Object> it =  jsonArray.iterator();
        List<IdentityRecord> identityRecords = new ArrayList<>();

        while (it.hasNext()){
            JSONObject item = (JSONObject) it.next();

            if(riskItemProperties.getIdentityRecordList().contains(item.getString("item_name"))){
                JSONArray frequency_detail_list = item.getJSONObject("item_detail").getJSONArray("frequency_detail_list");

                Iterator<Object> frequency_detail_list_it = frequency_detail_list.iterator();

                while (frequency_detail_list_it.hasNext()){
                    JSONObject frequency_detail_list_item = (JSONObject)  frequency_detail_list_it.next();

                    IdentityRecord identityRecord = new IdentityRecord();
                    identityRecord.setData(frequency_detail_list_item.getJSONArray("data").toJSONString());
                    identityRecord.setDetail((String) frequency_detail_list_item.getOrDefault("detail",DEFAULT_VALUE));
                    identityRecord.setType(item.getString("item_name"));
                    identityRecord.setRemark(reportId);
                    identityRecords.add(identityRecord);
                }

            }
        }

        return identityRecords;
    }



    @Override
    public List<LoanRecord> loanRecordHandle(JSONArray jsonArray, CreditRequest creditRequest,String reportId){
        Iterator<Object> it =  jsonArray.iterator();
        List<LoanRecord> loanRecords = new ArrayList<>();


        while (it.hasNext()) {
            JSONObject item = (JSONObject) it.next();

            if(riskItemProperties.getLoanRecordList().contains(item.getString("item_name"))){
                JSONObject item_detail = item.getJSONObject("item_detail");



                JSONArray platform_detail_dimensions = item_detail.getJSONArray("platform_detail_dimension");

                Iterator<Object> platform_detail_dimension_it =  platform_detail_dimensions.iterator();

                while (platform_detail_dimension_it.hasNext()){
                    LoanRecord loanRecord = new LoanRecord();
                    JSONObject platform_detail_dimension_item = (JSONObject) platform_detail_dimension_it.next();

                    loanRecord.setCount(String.valueOf(platform_detail_dimension_item.getOrDefault("count",DEFAULT_VALUE)));
                    loanRecord.setType(String.valueOf(platform_detail_dimension_item.getOrDefault("dimension",DEFAULT_VALUE)));

                    loanRecord.setDetail(platform_detail_dimension_item.getJSONArray("detail").toJSONString());
                    loanRecord.setRemark(reportId);
                    loanRecords.add(loanRecord);

                }


            }

        }

        return loanRecords;
    }


    // 黑名单记录
    @Override
    public List<NamelistRecord> namelistRecordHandle(JSONArray jsonArray, CreditRequest creditRequest,String reportId){
        Iterator<Object> it =  jsonArray.iterator();
        List<NamelistRecord> namelistRecords = new ArrayList<>();


        while (it.hasNext()) {
            JSONObject item = (JSONObject) it.next();
            if(riskItemProperties.getNamelistRecordList().contains(item.getString("item_name"))){
                JSONObject item_detail = item.getJSONObject("item_detail");
                if(item_detail!=null &&item_detail.containsKey("namelist_hit_details")){
                    JSONArray namelist_hit_details = item_detail.getJSONArray("namelist_hit_details");

                    Iterator<Object> namelist_hit_details_id = namelist_hit_details.iterator();

                    while (namelist_hit_details_id.hasNext()){
                        JSONObject namelist_hit_details_item = (JSONObject) namelist_hit_details_id.next();
                        NamelistRecord namelistRecord = new NamelistRecord();
                        namelistRecord.setType(item.getString("item_name"));
                        namelistRecord.setHitTypeDisplayName(String.valueOf(namelist_hit_details_item.getOrDefault("hit_type_displayname",DEFAULT_VALUE)));
                        namelistRecord.setDescription(String.valueOf(namelist_hit_details_item.getOrDefault("description",DEFAULT_VALUE)));
                        namelistRecord.setFraudType(String.valueOf(namelist_hit_details_item.getOrDefault("fraud_type",DEFAULT_VALUE)));
                        namelistRecord.setRemark(reportId);
                        namelistRecords.add(namelistRecord);
                    }
                }



            }


        }

            return namelistRecords;
    }

    @Override
    protected BaseDao getBaseDao() {
        return creditRequestMapper;
    }

}
