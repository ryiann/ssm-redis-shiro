package com.ryan.service.impl;

import com.ryan.dao.UserMapper;
import com.ryan.pojo.UserDO;
import com.ryan.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YoriChen
 * @date 2018/6/21
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDO selectUserInfoByUserName(String userName) {
        return userMapper.selectUserInfoByUserName(userName);
    }
}