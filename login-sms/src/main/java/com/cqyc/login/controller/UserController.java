package com.cqyc.login.controller;


import com.cqyc.login.comm.util.ValidUtils;
import com.cqyc.login.comm.util.jwt.JwtUtils;
import com.cqyc.login.domain.CommEntity;
import com.cqyc.login.domain.User;
import com.cqyc.login.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2019-10-26
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 登录
     */
    @PostMapping
    public CommEntity login(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam("imgCode") String imgCode,
                            @RequestParam("imgUuid") String imgUuid) {
        if (ValidUtils.checkIsContainChiese(username)) {
            return CommEntity.create("输入的用户名不能包含有汉字", 500);
        }
        if (StringUtils.isBlank(imgUuid)) {
            return CommEntity.create("获取验证码错误", 500);
        }
        return userService.login(username, password, imgUuid, imgCode);
    }


    @PostMapping("/refresh")
    public CommEntity refreshToken(@RequestParam("username") String username) {
        if (ValidUtils.checkIsContainChiese(username)) {
            return CommEntity.create("输入的用户名不能包含有汉字", 500);
        }
        return userService.refreshToken(username);
    }

    /**
     * 修改用户信息
     */
    @PutMapping("/user")
    public CommEntity updateUserInfo(@RequestBody @Validated User user, BindingResult result) {
        if (result.hasFieldErrors()) {
            if (result.hasFieldErrors()) {
                return CommEntity.create("输入的内容不能为空", 500);
            }
        }
        return userService.updateUserInfo(user);
    }


    /**
     * 修改密码
     */
    @PostMapping("/change/pass")
    public CommEntity updatePassword(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("telephone") String telephone,
                                     @RequestParam("veriyCode") String veriyCode) {
        String phoneCode = (String) redisTemplate.opsForHash().get("phoneCode", telephone);
        if (StringUtils.isBlank(phoneCode)) {
            return CommEntity.create("这是一个悲伤的故事,你可能还没发送短信或者已经失效了", 500);
        }
        if (!StringUtils.equals(veriyCode, phoneCode)) {
            return CommEntity.create("输入的验证码有误", 500);
        }
        return userService.updatePassword(username, password, newPassword);
    }


    @PostMapping("/exit")
    public CommEntity exitLogin(@RequestParam("username") String username) {
        Boolean hasKey = redisTemplate.hasKey(username + ":userInfo");
        if (hasKey == null || !hasKey) {
            return CommEntity.create("未登录或账号信息已过期", 500);
        }
        Boolean delete = redisTemplate.delete(username + ":userInfo");
        if (delete == null || !delete) {
            return CommEntity.create("因系统出现故障,无法退出用户", 500);
        }
        return CommEntity.create("退出成功", 200);
    }
}
