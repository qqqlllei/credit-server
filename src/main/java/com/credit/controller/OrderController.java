package com.credit.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lilei on 2017/5/2.
 */
@Controller
@RequestMapping("/order")
public class OrderController {


    protected Logger logger = LoggerFactory.getLogger(OrderController.class);




    @RequestMapping("/orderSpecialView")
    public String orderSpecial(HttpServletRequest request,Model model){

        return "/order/orderSpecial";
    }

    @RequestMapping("/listView")
    public String listView(){
        return "/order/orderList";
    }

}
