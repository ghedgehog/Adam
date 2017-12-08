package com.sunway.service.impl;

import com.sunway.mapper.IUserMapper;
import com.sunway.model.User;
import com.sunway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    IUserMapper usersMapper;

    @Override
    public User queryUser(String name, String pwd) {
        return usersMapper.queryUser(name, pwd);
    }

    @Override
    public boolean checkUser(String name, String pwd){
        //System.out.println("queryUser: " + usersMapper.queryUser(name, pwd));
        return usersMapper.queryUser(name, pwd) == null ? false : true;
    }
}
