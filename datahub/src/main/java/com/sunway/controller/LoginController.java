package com.sunway.controller;

import com.sunway.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    @RequestMapping(value="/starter")
    public String menu(HttpServletRequest request, Model model){
//        String userName = request.getParameter("userName");
//        String passWord = request.getParameter("passWord");
//        if (usersMapper.queryUser(userName, passWord) != null)
//        {
//            model.addAttribute("name", userName);
//            model.addAttribute("result", "true");
//        }
        return  "starter";
    }

    @RequestMapping(value="/template_manage")
    public String template_manage(){
        System.out.println("template_manage...");
        return "template_manage";
    }

    @RequestMapping(value="/template_info")
    public String template_info(@RequestBody Map<String, String> temp_map, Model model){
        String model_name = temp_map.get("model_name");
        System.out.println(model_name);
        System.out.println("进入了template_info...");
        model.addAttribute("model_name", model_name);
        return "template_info";
    }

    @RequestMapping(value="/create_device")
    public String create_device(){
        System.out.println("create_device...");
        return "create_device";
    }

    @RequestMapping(value="/create_var")
    public String create_var(){
        System.out.println("create_var...");
        return "create_var";
    }

    @RequestMapping(value="/device_model")
    public String device_model(){
        System.out.println("device_model...");
        return "device_model";
    }

    @RequestMapping(value="/device_view")
    public String device_view(){
        System.out.println("device_view...");
        return "device_view";
    }

    @RequestMapping(value="/linker_manage")
    public String linker_manage(){
        System.out.println("linker_manage...");
        return "linker_manage";
    }

    @RequestMapping(value="/linker_info")
    public String linker_info(){
        System.out.println("linker_info...");
        return "linker_info";
    }

    @RequestMapping(value="/var_manage")
    public String var_manage(@RequestBody Map<String, String> temp_map, Model model){
        String model_name = temp_map.get("model_name");
        System.out.println(model_name);
        model.addAttribute("model_name", model_name);
        System.out.println("进入了var_manage...");
        return "var_manage";
    }

    @RequestMapping(value="/data_monitor")
    public String data_monitor(){
        System.out.println("data_monitor...");
        return "data_monitor";
    }
}

