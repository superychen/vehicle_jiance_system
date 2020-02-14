package com.cqyc.auth.controller;

import com.cqyc.auth.config.MyUserDetailServiceImpl;
import com.cqyc.auth.domain.CommEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-13
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private MyUserDetailServiceImpl userDetailService;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("user")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/test")
    public String testMessage(@RequestParam("test_name") String test_name) {
        System.out.println("1111111");
        return test_name;
    }

    @DeleteMapping(value = "/exit")
    public CommEntity revokeToken(String access_token) {
        if (consumerTokenServices.revokeToken(access_token)) {
            return CommEntity.create("注销成功", 200);
        } else {
            return CommEntity.create("注销失败", 500);
        }
    }

}
