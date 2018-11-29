package com.github.lihang941.example.service.impl;

import com.github.lihang941.common.service.BaseMapperService;
import com.github.lihang941.common.mapper.BaseMapper;
import com.github.lihang941.example.dao.UserMapper;
import org.springframework.stereotype.Service;
import com.github.lihang941.example.service.UserService;
import com.github.lihang941.example.entity.User;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-11-29 17:54
 */
@Service
public class UserServiceImpl extends BaseMapperService<User, String> implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        super(userMapper);
        this.userMapper = userMapper;
    }

}
