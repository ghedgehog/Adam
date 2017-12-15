package com.sunway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service("realDataReceiver")
public class RealDataReceiver {

    /*private CountDownLatch latch;

    @Autowired
    public RealDataReceiver(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiveMessage(String message) {
        System.out.println("Received :" + message);
        latch.countDown();
    }*/
}
