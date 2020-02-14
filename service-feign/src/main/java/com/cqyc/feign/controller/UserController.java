package com.cqyc.feign.controller;

import com.cqyc.feign.domain.UserLoginDTO;
import com.cqyc.feign.service.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-13
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceDetail userServiceDetail;

    @PostMapping("login")
    public UserLoginDTO loginUser(@RequestParam("username") String username,
                                  @RequestParam("password") String password) {
        return userServiceDetail.loginUser(username,password);
    }

    @GetMapping("/foo")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getFoo() {
        return "i'm foo, " + UUID.randomUUID().toString();
    }
}
