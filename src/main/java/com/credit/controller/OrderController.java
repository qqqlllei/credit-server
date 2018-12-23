package com.credit.controller;


import com.alibaba.fastjson.JSONObject;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by lilei on 2017/5/2.
 */
@Controller
@RequestMapping("/")
public class OrderController {



    @Autowired
    private WxMpService wxMpService;



    protected Logger logger = LoggerFactory.getLogger(OrderController.class);




    @RequestMapping("/userInfo")
    public String orderSpecial(HttpServletRequest request,Model model){


        try {
            String uri = request.getRequestURL()+ (request.getQueryString() == null ? "" : "?"+ request.getQueryString());
            WxJsapiSignature wxJsapiSignature =  wxMpService.createJsapiSignature(uri);
            model.addAttribute("wxJsapiSignature",wxJsapiSignature);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return "/user/userInfo";
    }


}
