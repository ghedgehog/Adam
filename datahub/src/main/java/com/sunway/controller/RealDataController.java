package com.sunway.controller;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RealDataController {
    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valOpsObj;

}
