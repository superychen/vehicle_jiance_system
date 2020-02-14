package com.cqyc.feign.service.impl;

import com.cqyc.feign.domain.JWT;
import com.cqyc.feign.domain.User;
import com.cqyc.feign.domain.UserLoginDTO;
import com.cqyc.feign.feign.AuthServiceClient;
import com.cqyc.feign.mapper.UserMapper;
import com.cqyc.feign.service.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: cqyc
 * Description: 用户登录实践类
 * Created by cqyc on 20-1-14
 */
@Service
public class UserServiceDetailImpl implements UserServiceDetail {
    private final static Integer TIME_OUT = 60 * 60 * 24;

    @Autowired
    private AuthServiceClient client;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public UserLoginDTO loginUser(String username, String password) {

        JWT jwt = client.postAccessToken("Basic Y3F5YzoxMjM0NTY=", "password", username, password);
        //将用户信息暂时存到缓存中，包括密码(用于刷新jwt token),过期时间为30分钟
        redisTemplate.opsForValue().set(username + ":token", jwt.getAccess_token(), 1800, TimeUnit.SECONDS);
        if (jwt == null) {
            throw new RuntimeException("jwt为空");
        }
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.setJwt(jwt);
///        userDTO.setUser(securityUser);
        return userDTO;
    }
}
