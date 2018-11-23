//package com.credit.controller;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.credit.entity.*;
//import com.credit.service.CreditRequestService;
//import com.credit.utils.DateTime;
//import com.credit.utils.HttpClientUtil;
//import com.credit.utils.MD5;
//import com.credit.utils.SortMap;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import java.sql.Timestamp;
//import java.util.*;
//
///**
// * Created by Administrator on 2018/10/20 0020.
// */
//@Controller
//@RequestMapping("/credit")
//public class CreditRequestController {
//
//    @Value("${credit.appId}")
//    private String appId;
//    @Value("${credit.appKey}")
//    private String appKey;
//
//    @Value("${credit.url}")
//    private String appUrl;
//
//    @Autowired
//    private CreditRequestService creditRequestService;
//
//    @RequestMapping("/submit")
//    public String submit(HttpServletRequest request, Model model){
//        String phone = request.getParameter("phone");
//        String name = request.getParameter("name");
//        String idcard = request.getParameter("idcard");
//        CreditRequest creditRequest = creditRequestService.getCreditRequestByPhoneAndIdCard(phone,idcard);
//        if(creditRequest !=null){
//            DateTime createTime = new DateTime(creditRequest.getCreateTime().getTime());
//            int days = createTime.dayNumFrom(new DateTime(System.currentTimeMillis()));
//            if(days <= 30){
//                JSONObject queryInfoBody = JSONObject.parseObject(creditRequest.getInfo());
//
//                JSONObject riskBody = JSONObject.parseObject(queryInfoBody.getString("body"));
//                JSONArray jsonArray = riskBody.getJSONArray("risk_items");
//
//                handleData(model,jsonArray,creditRequest);
//                model.addAttribute("name",name);
//                return "/creditDetail/creditDetail";
//            }
//        }
//        Long timestamp = System.currentTimeMillis();
//        String sign = MD5.MD5Encode(appId+timestamp+appKey);
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("appId",appId);
//        requestBody.put("timestamp",timestamp);
//        requestBody.put("sign",sign);
//        requestBody.put("phone",phone);
//        requestBody.put("name",name);
//        requestBody.put("idcard",idcard);
//
//        System.out.println("======================="+requestBody.toJSONString()+"======================");
//        String submitInfo = HttpClientUtil.unEncodingPost(requestBody.toJSONString(),appUrl+"submit");
//        System.out.println("=======================submitInfo"+submitInfo+"======================");
//        JSONObject submitResult = JSONObject.parseObject(submitInfo);
//        if(!submitResult.containsKey("code") || !"200".equals(submitResult.getString("code"))) return "/error";
//        String queryInfo = HttpClientUtil.unEncodingPost(requestBody.toJSONString(),appUrl+"query");
//        CreditRequest creditRequestInfo = new CreditRequest();
//        creditRequestInfo.setName(name);
//        creditRequestInfo.setPhone(phone);
//        creditRequestInfo.setIdcard(idcard);
//        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
//        creditRequestInfo.setCreateTime(currentTime);
//        creditRequestInfo.setUpdateTime(currentTime);
//        creditRequestInfo.setInfo(queryInfo);
//        JSONObject queryInfoBody = JSONObject.parseObject(queryInfo);
//        JSONObject riskBody = JSONObject.parseObject(queryInfoBody.getString("body"));
//        String reportId=riskBody.getString("report_id");
//        creditRequestInfo.setRemark(reportId);
//        creditRequestInfo.setStatus("0");
//        creditRequestService.saveCreditRequest(creditRequestInfo);
//
//
//        JSONArray jsonArray = riskBody.getJSONArray("risk_items");
//
//        handleData(model,jsonArray,creditRequestInfo);
//        model.addAttribute("name",name);
//        return "/creditDetail/creditDetail";
//    }
//
//    @RequestMapping("/query")
//    public String query(HttpServletRequest request,Model model){
//        String phone = request.getParameter("phone");
//        String name = request.getParameter("name");
//        String idcard = request.getParameter("idcard");
//
//        model.addAttribute("phone",phone);
//        model.addAttribute("name",name);
//        model.addAttribute("idcard",idcard);
//
//        return "/query/query";
//    }
//
//    @RequestMapping("/detail")
//    public String detail( Model model){
//
//        model.addAttribute("totalScore",new Random().nextInt(100));
//        return "/creditDetail/creditDetail";
//    }
//
//    @RequestMapping("/share")
//    public String share(String name,String totalScore,Model model){
//
//        model.addAttribute("name",name);
//        model.addAttribute("totalScore",totalScore);
//        return "/shareResult/shareResult";
//    }
//
//    private void handleData(Model model,JSONArray jsonArray,CreditRequest creditRequest){
//
//        model.addAttribute("hasData",true);
//        if(jsonArray.size()==0){
//            model.addAttribute("hasData",false);
//
//        }
//
//
//        //法院信息
//        List<JudicialRecord> courtInfoList = creditRequestService.courtcInfoHandle(jsonArray,creditRequest,creditRequest.getRemark());
//
//        //身份记录
//        List<IdentityRecord> identityRecords =  creditRequestService.identityRecordHandle(jsonArray,creditRequest,creditRequest.getRemark());
//
//        //逾期记录
//        List<OverdueRecord> overdueRecords =   creditRequestService.overdueRecordHandle(jsonArray,creditRequest,creditRequest.getRemark());
//
//        //借贷记录
//        List<LoanRecord> loanRecords =   creditRequestService.loanRecordHandle(jsonArray,creditRequest,creditRequest.getRemark());
//
//
//        //黑名单
//        List<NamelistRecord> namelistRecords = creditRequestService.namelistRecordHandle(jsonArray,creditRequest,creditRequest.getRemark());
//
//        for (IdentityRecord identityRecord :identityRecords) {
//            identityRecord.setDatas(JSONArray.parseArray(identityRecord.getData()));
//        }
//
//
//        for (OverdueRecord overdueRecord :overdueRecords) {
//            overdueRecord.setDatas(JSONArray.parseArray(overdueRecord.getDescription()));
//        }
//
//        Map<String, Integer> sevenDays = new HashMap<>();
//        Map<String, Integer> oneMonth = new HashMap();
//        Map<String, Integer> threeMonth = new HashMap();
//        for (LoanRecord loanRecord :loanRecords) {
//            if(loanRecord.getDescription().startsWith("7天内")){
//
//                if(loanRecord.getType().contains("手机")){
//                    sevenDays.put("phoneCount",Integer.valueOf(loanRecord.getCount()));
//                }
//                if(loanRecord.getType().contains("身份证")){
//                    sevenDays.put("idCardCount",Integer.valueOf(loanRecord.getCount()));
//                }
//
//                JSONArray list = JSONArray.parseArray(loanRecord.getDetail());
//                Iterator<Object> iterable =  list.iterator();
//                while (iterable.hasNext()){
//                    String data  = (String) iterable.next();
//                    String[] temp = data.split(":");
//                    Integer count = 0 ;
//                    if(sevenDays.containsKey("sevenDay"+temp[0])){
//                        count = Integer.valueOf(sevenDays.get("sevenDay"+temp[0]));
//                    }
//                    sevenDays.put("sevenDay"+temp[0],count+Integer.valueOf(temp[1]));
//                }
//            }else if(loanRecord.getDescription().startsWith("1个月内")){
//
//                if(loanRecord.getType().contains("手机")){
//                    oneMonth.put("phoneCount",Integer.valueOf(loanRecord.getCount()));
//                }
//                if(loanRecord.getType().contains("身份证")){
//                    oneMonth.put("idCardCount",Integer.valueOf(loanRecord.getCount()));
//                }
//
//                JSONArray list = JSONArray.parseArray(loanRecord.getDetail());
//                Iterator<Object> iterable =  list.iterator();
//                while (iterable.hasNext()){
//                    String data  = (String) iterable.next();
//                    String[] temp = data.split(":");
//                    Integer count = 0 ;
//                    if(oneMonth.containsKey("oneMonth"+temp[0])){
//                        count = Integer.valueOf(oneMonth.get("oneMonth"+temp[0]));
//                    }
//                    oneMonth.put("oneMonth"+temp[0],count+Integer.valueOf(temp[1]));
//                }
//            }else{
//                if(loanRecord.getType().contains("手机")){
//                    threeMonth.put("phoneCount",Integer.valueOf(loanRecord.getCount()));
//                }
//                if(loanRecord.getType().contains("身份证")){
//                    threeMonth.put("idCardCount",Integer.valueOf(loanRecord.getCount()));
//                }
//
//                JSONArray list = JSONArray.parseArray(loanRecord.getDetail());
//                Iterator<Object> iterable =  list.iterator();
//                while (iterable.hasNext()){
//                    String data  = (String) iterable.next();
//                    String[] temp = data.split(":");
//                    Integer count = 0 ;
//                    if(threeMonth.containsKey("threeMonth"+temp[0])){
//                        count = Integer.valueOf(threeMonth.get("threeMonth"+temp[0]));
//                    }
//                    threeMonth.put("threeMonth"+temp[0],count+Integer.valueOf(temp[1]));
//                }
//            }
//
//        }
//
//        if(!sevenDays.containsKey("phoneCount")) sevenDays.put("phoneCount",0);
//        if(!sevenDays.containsKey("idCardCount")) sevenDays.put("idCardCount",0);
//
//        if(!oneMonth.containsKey("phoneCount")) oneMonth.put("phoneCount",0);
//        if(!oneMonth.containsKey("idCardCount")) oneMonth.put("idCardCount",0);
//
//        if(!threeMonth.containsKey("phoneCount")) threeMonth.put("phoneCount",0);
//        if(!threeMonth.containsKey("idCardCount")) threeMonth.put("idCardCount",0);
//
//
//
//
//
//
//        Map<String, Integer> sortSevenDays = SortMap.sortMapByValue(sevenDays);
//        model.addAttribute("sevenDays",sortSevenDays);
//
//        Map<String, Integer> sortOneMonth = SortMap.sortMapByValue(oneMonth);
//        model.addAttribute("oneMonth",sortOneMonth);
//
//        Map<String, Integer> sortThreeMonth = SortMap.sortMapByValue(threeMonth);
//        model.addAttribute("threeMonth",sortThreeMonth);
//
//        model.addAttribute("courtInfoList",courtInfoList);
//        model.addAttribute("identityRecords",identityRecords);
//        model.addAttribute("overdueRecords",overdueRecords);
//        model.addAttribute("loanRecords",loanRecords);
//
//        model.addAttribute("namelistRecords",namelistRecords);
//
//        int totalScore=95;
//
//
//        //司法记录分数计算
//        int courInfoScore = 5*courtInfoList.size();
//        if(courInfoScore >= 20){
//            totalScore= 75;
//        }else {
//            totalScore= totalScore-courInfoScore;
//        }
//
//        //黑名单分数计算
//        int namelistScore = namelistRecords.size()*4;
//        totalScore= totalScore-namelistScore;
//
//        //身份记录
//        if(identityRecords.size()>0){
//            totalScore= totalScore-12;
//        }
//
//        //借贷记录
//
//        int loanRecordScore = 2*loanRecords.size();
//        if(loanRecordScore >= 18){
//            totalScore= totalScore-18;
//        }else {
//            totalScore= totalScore-loanRecordScore;
//        }
//
//
//        // 逾期记录扣分
//        for (int j=0;j<overdueRecords.size();j++){
//            OverdueRecord overdueRecord = overdueRecords.get(j);
//            JSONArray overdue_details = JSONObject.parseArray(overdueRecord.getDescription());
//            int currentMaxScore=0;
//            for (int i=0;i<overdue_details.size();i++){
//                JSONObject overdue_detail = (JSONObject) overdue_details.get(i);
//                String range = overdue_detail.getString("overdue_amount_range");
//
//                if("(0, 1000]".equals(range)){
//                    if(currentMaxScore < 5){
//                        currentMaxScore = 5;
//                    }
//                }
//                if("(1000, 5000]".equals(range)){
//                    if(currentMaxScore < 10){
//                        currentMaxScore = 10;
//                    }
//                }
//                if("(5000, 10000]".equals(range)){
//                    if(currentMaxScore < 20){
//                        currentMaxScore = 20;
//                    }
//                }
//                if("(10000, 50000]".equals(range)){
//                    if(currentMaxScore < 20){
//                        currentMaxScore = 20;
//                    }
//                }
//                if("(50000, 100000]".equals(range)){
//                    if(currentMaxScore < 20){
//                        currentMaxScore = 20;
//                    }
//                }
//                if("(100000, 500000]".equals(range)){
//                    if(currentMaxScore < 20){
//                        currentMaxScore = 20;
//                    }
//                }
//                if("500000+".equals(range)){
//                    if(currentMaxScore < 20){
//                        currentMaxScore = 20;
//                    }
//                }
//
//
//            }
//            totalScore= totalScore-currentMaxScore;
//        }
//
//        if(totalScore >= 90){
//            model.addAttribute("homeCount",0);
//            model.addAttribute("percent",0);
//        }
//
//
//
//
//        int a = (90-totalScore) / 5 ;
//
//        int b = (90-totalScore) % 5 ;
//        int c=0;
//        if(a >0 ) {
//            c = a;
//        }
//        if( b>0){
//            c= c+1;
//        }
//
//
//        int d = (90-totalScore) / 2 ;
//
//        int e = (90-totalScore) % 2 ;
//        int f=0;
//        if(d >0 ) {
//            f = d;
//        }
//        if( e>0){
//            f= f+1;
//        }
//
//        if(f >0){
//            f=f+5;
//        }
//
//        if(c>=9) c=9;
//        if(f>=70) f=70;
//
//        model.addAttribute("homeCount",c);
//        model.addAttribute("percent",f);
//
//
//
//        if(totalScore<0) {
//            totalScore = 23;
//        }
//
//        model.addAttribute("totalScore",totalScore);
//    }
//
//
//
//}