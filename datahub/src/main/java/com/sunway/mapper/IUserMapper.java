package com.sunway.mapper;

import com.sunway.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface IUserMapper {
    public User queryUser(String name, String pwd);
}
