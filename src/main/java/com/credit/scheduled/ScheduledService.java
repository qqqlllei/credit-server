package com.credit.scheduled;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.credit.entity.CreditRequest;
import com.credit.service.CreditRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ScheduledService {


    @Autowired
    private CreditRequestService creditRequestService;

    private static final String unDo="0";


    @Scheduled(cron = "0/60 * * * * *")
    public void scheduled(){


        List<CreditRequest> creditRequestList =  creditRequestService.getCreditRequestByStatus(unDo);

        for (CreditRequest creditRequest : creditRequestList) {

            JSONObject queryInfoBody = JSONObject.parseObject(creditRequest.getInfo());
            JSONArray jsonArray = JSONObject.parseObject(queryInfoBody.getString("body")).getJSONArray("risk_items");
            creditRequestService.saveCreditInfo(jsonArray,creditRequest);

        }

    }
}