package com.cqyc.ribbon.service.impl;

import com.cqyc.ribbon.service.RibbonService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-12
 */
@Service
public class RibbonServiceImpl implements RibbonService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @HystrixCommand(fallbackMethod = "errorHi")
    public String ribbonHi(String name) {
        String forObject = restTemplate.getForObject("http://SERVICE-DEMO/hi?name=" + name, String.class);
        return forObject;
    }


    public String errorHi(String name){
        return "出现了意想不到的错误！！！";
    }

}
