package com.credit.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.credit.entity.*;
import com.credit.service.CreditRequestService;
import com.credit.utils.DateTime;
import com.credit.utils.HttpClientUtil;
import com.credit.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2018/10/20 0020.
 */
@Controller
@RequestMapping("/credit")
public class CreditRequestController {

    @Value("${credit.appId}")
    private String appId;
    @Value("${credit.appKey}")
    private String appKey;

    @Value("${credit.url}")
    private String appUrl;

    @Autowired
    private CreditRequestService creditRequestService;

    @RequestMapping("/submit")
    public String submit(HttpServletRequest request, Model model){
        String phone = request.getParameter("phone");
        String name = request.getParameter("name");
        phone="15555555555";
        String idcard = request.getParameter("idcard");
        CreditRequest creditRequest = creditRequestService.getCreditRequestByPhone(phone);
        if(creditRequest !=null){
            DateTime createTime = new DateTime(creditRequest.getCreateTime().getTime());
            int days = createTime.dayNumFrom(new DateTime(System.currentTimeMillis()));
            if(days <= 30){
                JSONObject queryInfoBody = JSONObject.parseObject(creditRequest.getInfo());

                JSONObject riskBody = JSONObject.parseObject(queryInfoBody.getString("body"));
                JSONArray jsonArray = riskBody.getJSONArray("risk_items");

                //法院信息
                List<JudicialRecord> courtInfoList = creditRequestService.courtcInfoHandle(jsonArray,creditRequest,creditRequest.getRemark());

                //身份记录
                List<IdentityRecord> identityRecords =  creditRequestService.identityRecordHandle(jsonArray,creditRequest,creditRequest.getRemark());

                //逾期记录
                List<OverdueRecord> overdueRecords =   creditRequestService.overdueRecordHandle(jsonArray,creditRequest,creditRequest.getRemark());

                //借贷记录
                List<LoanRecord> loanRecords =   creditRequestService.loanRecordHandle(jsonArray,creditRequest,creditRequest.getRemark());


                //黑名单
                List<NamelistRecord> namelistRecords = creditRequestService.namelistRecordHandle(jsonArray,creditRequest,creditRequest.getRemark());

                for (IdentityRecord identityRecord :identityRecords) {
                    identityRecord.setDatas(JSONArray.parseArray(identityRecord.getData()));
                }

                model.addAttribute("courtInfoList",courtInfoList);
                model.addAttribute("identityRecords",identityRecords);
                model.addAttribute("overdueRecords",overdueRecords);
                model.addAttribute("loanRecords",loanRecords);

                model.addAttribute("namelistRecords",namelistRecords);

                int totalScore=100;

               int courInfoScore = 5*courtInfoList.size();
                if(courInfoScore >= 20){
                    totalScore= 80;
                }else {
                    totalScore= totalScore-courInfoScore;
                }


                int overdueScore = 15*overdueRecords.size();
                totalScore= totalScore - overdueScore;



                for (int j=0;j<overdueRecords.size();j++){
                    OverdueRecord overdueRecord = overdueRecords.get(j);
                    JSONArray overdue_details = JSONObject.parseArray(overdueRecord.getDescription());

                    for (int i=0;i<overdue_details.size();i++){
                        JSONObject overdue_detail = (JSONObject) overdue_details.get(i);
                        String range = overdue_detail.getString("overdue_amount_range");
                        if("(0, 1000]".equals(range)){
                            totalScore=totalScore-5;
                        }
                        if("(1000, 5000]".equals(range)){
                            totalScore=totalScore-10;
                        }
                        if("(5000, 10000]".equals(range)){
                            totalScore=totalScore-20;
                        }
                        if("(10000, 50000]".equals(range)){
                            totalScore=totalScore-20;
                        }
                        if("(50000, 100000]".equals(range)){
                            totalScore=totalScore-20;
                        }
                        if("(100000, 500000]".equals(range)){
                            totalScore=totalScore-20;
                        }
                        if("500000+".equals(range)){
                            totalScore=totalScore-20;
                        }
                    }
                }



                model.addAttribute("totalScore",totalScore<0?0:totalScore);



                return "/creditDetail/creditDetail";
            }
        }
        Long timestamp = System.currentTimeMillis();
        String sign = MD5.MD5Encode(appId+timestamp+appKey);
        JSONObject requestBody = new JSONObject();
        requestBody.put("appId",appId);
        requestBody.put("timestamp",timestamp);
        requestBody.put("sign",sign);
        requestBody.put("phone",phone);
        requestBody.put("name",name);
        requestBody.put("idcard",idcard);

        System.out.println("======================="+requestBody.toJSONString()+"======================");
        String submitInfo = HttpClientUtil.unEncodingPost(requestBody.toJSONString(),appUrl+"submit");

        String queryInfo = HttpClientUtil.unEncodingPost(requestBody.toJSONString(),appUrl+"query");
        CreditRequest creditRequestInfo = new CreditRequest();
        creditRequestInfo.setName(name);
        creditRequestInfo.setPhone(phone);
        creditRequestInfo.setIdcard(idcard);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        creditRequestInfo.setCreateTime(currentTime);
        creditRequestInfo.setUpdateTime(currentTime);
        creditRequestInfo.setInfo(queryInfo);
        JSONObject queryInfoBody = JSONObject.parseObject(queryInfo);
        JSONObject riskBody = JSONObject.parseObject(queryInfoBody.getString("body"));
        String reportId=riskBody.getString("report_id");
        creditRequestInfo.setRemark(reportId);
        creditRequestInfo.setStatus("0");
        creditRequestService.saveCreditRequest(creditRequestInfo);


//        JSONArray jsonArray = riskBody.getJSONArray("risk_items");
//
//        List<JudicialRecord> courtInfoList = creditRequestService.courtcInfoHandle(jsonArray,creditRequest,reportId);
//
//        List<IdentityRecord> identityRecords =  creditRequestService.identityRecordHandle(jsonArray,creditRequest,reportId);
//
//        List<OverdueRecord> overdueRecords =   creditRequestService.OverdueRecordHandle(jsonArray,creditRequest);
//
//        List<LoanRecord> loanRecords =   creditRequestService.loanRecordHandle(jsonArray,creditRequest,reportId);
//
//        model.addAttribute("courtInfoList",courtInfoList);
//        model.addAttribute("identityRecords",identityRecords);
//        model.addAttribute("overdueRecords",overdueRecords);
//        model.addAttribute("loanRecords",loanRecords);
        return "/creditDetail/creditDetail";
    }

    @RequestMapping("/query")
    public String query(){

        return "/query/query";
    }

    @RequestMapping("/detail")
    public String detail( Model model){

        model.addAttribute("totalScore",new Random().nextInt(100));
        return "/creditDetail/creditDetail";
    }

    @RequestMapping("/share")
    public String share(){

        return "/shareResult/shareResult";
    }

}
