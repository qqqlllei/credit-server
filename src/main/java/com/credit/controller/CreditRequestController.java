package com.credit.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.credit.domain.entity.CreditRequest;
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
import java.sql.Timestamp;
import java.util.Iterator;

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
    @ResponseBody
    public String submit(HttpServletRequest request, Model model){
        String phone = request.getParameter("phone");
        phone="15555555555";
        String name = request.getParameter("name");
        name="皮晴晴";
        String idcard = request.getParameter("idcard");
        idcard="370404199006301915";
        CreditRequest creditRequest = creditRequestService.getCreditRequestByPhone(phone);
        if(creditRequest !=null){
            DateTime createTime = new DateTime(creditRequest.getCreateTime().getTime());
            int days = createTime.dayNumFrom(new DateTime(System.currentTimeMillis()));
            if(days <= 30){
                JSONObject queryInfoBody = JSONObject.parseObject(creditRequest.getInfo());
                model.addAttribute("queryBody",JSONObject.parseObject(queryInfoBody.getString("body")));


                JSONArray jsonArray = JSONObject.parseObject(queryInfoBody.getString("body")).getJSONArray("risk_items");

                aaa(jsonArray);

                return "/user/userInfo";
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
        String submitInfo = HttpClientUtil.unEncodingPost(requestBody.toJSONString(),appUrl+"submit");

        String queryInfo = HttpClientUtil.unEncodingPost(requestBody.toJSONString(),appUrl+"query");
//        String queryInfo="{\"code\":200,\"msg\":\"success\",\"body\":{\"success\":true,\"risk_items\":[{\"risk_level\":\"medium\",\"score\":20,\"item_detail\":{\"frequency_detail_list\":[{\"detail\":\"1小时内手机号申请次数：4\"}],\"type\":\"frequency_detail\"},\"item_id\":2685821,\"item_name\":\"1小时内身份证或手机号申请次数大于等于3\",\"group\":\"客户行为检测\"},{\"risk_level\":\"low\",\"score\":6,\"item_detail\":{\"frequency_detail_list\":[{\"data\":[\"110223199011015315\",\"110223199011013333\"],\"detail\":\"3个月手机号关联身份证数：2\"}],\"type\":\"frequency_detail\"},\"item_id\":2685859,\"item_name\":\"3个月内申请信息关联多个身份证\",\"group\":\"客户行为检测\"},{\"risk_level\":\"low\",\"score\":5,\"item_detail\":{\"frequency_detail_list\":[{\"detail\":\"7天内手机号申请次数：4\"}],\"type\":\"frequency_detail\"},\"item_id\":2685877,\"item_name\":\"7天内设备或身份证或手机号申请次数过多\",\"group\":\"客户行为检测\"}],\"report_id\":\"ER2018102116014048C68DED\",\"application_id\":\"1810211601404404C72473F315C70C32\",\"final_decision\":\"Review\",\"final_score\":20,\"apply_time\":1540108900000,\"report_time\":1540108900000,\"address_detect\":{\"mobile_address\":\"北京市\",\"id_card_address\":\"北京市通县\"}}}";

        return "[param="+requestBody.toJSONString()+"][url="+appUrl+"]"+"[submitInfo="+submitInfo+"]"+"[queryInfo="+queryInfo+"";
//        CreditRequest creditRequestInfo = new CreditRequest();
//        creditRequestInfo.setName(name);
//        creditRequestInfo.setPhone(phone);
//        creditRequestInfo.setIdcard(idcard);
//        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
//        creditRequestInfo.setCreateTime(currentTime);
//        creditRequestInfo.setUpdateTime(currentTime);
//        creditRequestInfo.setInfo(queryInfo);
//        creditRequestService.saveCreditRequest(creditRequestInfo);
//        JSONObject queryInfoBody = JSONObject.parseObject(queryInfo);
//        if("200".equals(queryInfoBody.getString("code"))){
//            model.addAttribute("queryBody",queryInfoBody.getString("body"));
//        }
//        return "/user/userInfo";
    }

    private void aaa(JSONArray list){
        Iterator<Object> it =  list.iterator();
        while (it.hasNext()){
           JSONObject item = (JSONObject) it.next();
            if("身份证命中法院失信名单".equals(item.getString("item_name"))){
                System.out.println(item.getString("score"));
            }

        }
    }

}
