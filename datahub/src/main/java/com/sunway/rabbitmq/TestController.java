package com.sunway.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="mqtt")
public class TestController {

    @Autowired
    private Sender sender;

    @RequestMapping(value = "/sender")
    public void test(){
        sender.send();
    }
}
