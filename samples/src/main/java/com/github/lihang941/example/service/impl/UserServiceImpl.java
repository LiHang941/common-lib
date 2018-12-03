package com.github.lihang941.example.service.impl;

import com.github.lihang941.common.service.BaseMapperService;
import org.springframework.stereotype.Service;
import com.github.lihang941.example.service.UserService;
import com.github.lihang941.example.dao.UserMapper;
import com.github.lihang941.example.entity.User;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-12-03 10:02
 */
@Service
public class UserServiceImpl extends BaseMapperService<User, String> implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        super(userMapper);
        this.userMapper = userMapper;
    }

}
