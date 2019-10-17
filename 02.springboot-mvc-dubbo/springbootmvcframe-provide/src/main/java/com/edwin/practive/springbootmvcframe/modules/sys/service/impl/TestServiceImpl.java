package com.edwin.practive.springbootmvcframe.modules.sys.service.impl;

import com.edwin.practive.springbootmvcframe.modules.sys.service.ITestService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(version = "1.0.0",timeout = 10000,interfaceClass = ITestService.class)
public class TestServiceImpl implements ITestService{


    @Override
    public void test() {
        System.out.println(123123);
    }
}
