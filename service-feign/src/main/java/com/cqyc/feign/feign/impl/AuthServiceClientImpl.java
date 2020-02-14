package com.cqyc.feign.feign.impl;

import com.cqyc.feign.domain.JWT;
import com.cqyc.feign.feign.AuthServiceClient;
import org.springframework.stereotype.Component;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-14
 */
@Component
public class AuthServiceClientImpl implements AuthServiceClient {

    @Override
    public JWT postAccessToken(String authorization, String type, String username, String password) {
        System.out.println("进入了熔断");
        return null;
    }

    @Override
    public String testMessage(String test_name) {
        System.out.println("进入了熔断");
        return null;
    }
}
