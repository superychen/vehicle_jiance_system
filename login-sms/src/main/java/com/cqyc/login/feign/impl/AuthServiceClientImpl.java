package com.cqyc.login.feign.impl;

import com.cqyc.login.domain.JWT;
import com.cqyc.login.feign.AuthServiceClient;
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
