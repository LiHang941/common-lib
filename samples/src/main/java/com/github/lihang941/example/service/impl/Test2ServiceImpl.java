package com.github.lihang941.example.service.impl;

import com.github.lihang941.common.service.BaseMapperService;
import org.springframework.stereotype.Service;
import com.github.lihang941.example.service.Test2Service;
import com.github.lihang941.example.dao.Test2Mapper;
import com.github.lihang941.example.entity.Test2;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-12-03 10:02
 */
@Service
public class Test2ServiceImpl extends BaseMapperService<Test2, Long> implements Test2Service {

    private Test2Mapper test2Mapper;

    public Test2ServiceImpl(Test2Mapper test2Mapper) {
        super(test2Mapper);
        this.test2Mapper = test2Mapper;
    }

}
