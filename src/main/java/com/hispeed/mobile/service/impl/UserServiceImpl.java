package com.hispeed.mobile.service.impl;

import com.hispeed.mobile.domain.User;
import com.hispeed.mobile.mapper.UserInfoMapper;
import com.hispeed.mobile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dengtianguang on 2017/7/10.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 注入接口
     */
    @Autowired
    UserInfoMapper userInfoMapper;

    public int insertUserInfo(User user) {
        return userInfoMapper.insertUserInfo(user);
    }
}
