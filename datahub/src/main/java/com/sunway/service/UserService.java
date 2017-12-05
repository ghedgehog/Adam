package com.sunway.service;

import com.sunway.model.User;

public interface UserService {

    User queryUser(String name, String pwd);

    boolean checkUser(String name, String pwd);
}
