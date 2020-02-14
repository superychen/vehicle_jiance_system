package com.cqyc.login.service;

import com.cqyc.login.domain.CommEntity;
import com.cqyc.login.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cqyc.login.domain.vo.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cqyc
 * @since 2019-10-26
 */
public interface IUserService extends IService<User> {

    /**
     * 登录业务层
     * @param username 用户名
     * @param password 密码
     * @return ok
     */
    CommEntity login(String username, String password,String imgUuid,String imgCode);

    /**
     *  刷新token
     * @param username 用户名
     * @return 刷新的token
     */
    CommEntity refreshToken(String username);


    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 用户修改是否成功
     */
    CommEntity updateUserInfo(User user);

    /**
     * 修改用户密码
     * @param username 用户名
     * @param password 密码
     * @param newPassword 新密码
     * @return
     */
    CommEntity updatePassword(String username, String password, String newPassword);

    /**
     * 判断当前用户是否已经被注册
     * @param username　用户名
     * @return　ｃｏｍｍ
     */
    CommEntity isRegisterUser(String username);

    /**
     * 注册账户
     * @param user
     * @return
     */
    CommEntity register(UserVo user);
}
