package com.github.lihang941.example.service.impl;

import com.github.lihang941.common.service.BaseMapperService;
import com.github.lihang941.common.mapper.BaseMapper;
import com.github.lihang941.example.dao.TestMapper;
import org.springframework.stereotype.Service;
import com.github.lihang941.example.service.TestService;
import com.github.lihang941.example.entity.Test;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-11-29 17:54
 */
@Service
public class TestServiceImpl extends BaseMapperService<Test, Long> implements TestService {

    private TestMapper testMapper;

    public TestServiceImpl(TestMapper testMapper) {
        super(testMapper);
        this.testMapper = testMapper;
    }

}
