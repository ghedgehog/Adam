package com.sunway.controller;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RealData {
    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valOpsObj;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/menu")
    public String menu(HttpServletRequest request, Model model){
        String usrName = request.getParameter("userName");
        System.out.print("UsrName:" + usrName);
        model.addAttribute("key", "value");
        return "menu";
    }
}
