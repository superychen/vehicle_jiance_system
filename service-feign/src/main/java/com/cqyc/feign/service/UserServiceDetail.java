package com.cqyc.feign.service;

import com.cqyc.feign.domain.UserLoginDTO;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-14
 */
public interface UserServiceDetail {
    /**
     * 账号登录
     * @param username　用户名
     * @param password　密码
     * @return　用户
     */
    UserLoginDTO loginUser(String username, String password);
}
