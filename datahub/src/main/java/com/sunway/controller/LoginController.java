package com.sunway.controller;

import com.sunway.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登陆
 */
@Controller
public class LoginController {

    /*@Autowired
    private UserServiceImpl userService;*/

    @Autowired
    IUserMapper usersMapper;

    @RequestMapping(value="/index")
    public String index(){
        System.out.println("index...");
        return "login";
    }

    @RequestMapping(value="/menu")
    public String login(HttpServletRequest request, Model model){
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        if (usersMapper.queryUser(userName, passWord) != null)
        {
            model.addAttribute("name", userName);
            model.addAttribute("result", "true");
        }
        return  null;
    }
}

