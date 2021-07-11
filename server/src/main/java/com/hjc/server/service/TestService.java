package com.hjc.server.service;

import com.hjc.server.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    TestMapper testMapper;

    public String getTestList(){
        return testMapper.selectByPrimaryKey("1").toString();
    }
}
