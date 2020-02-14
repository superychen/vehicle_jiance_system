package com.cqyc.login.controller;


import com.alibaba.fastjson.JSONObject;
import com.cqyc.login.domain.CommEntity;
import com.cqyc.login.service.IWebsiteCountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.time.LocalDate;

/**
 * <p>
 * 网站统计表 前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2020-01-23
 */
@RestController
@RequestMapping("/website")
public class WebsiteCountController {

    @Autowired
    private IWebsiteCountService websiteCountService;

    @GetMapping("/registe")
    public CommEntity countRegister() {
        LocalDate date = LocalDate.now();
        //统计前五个月的注册数量
        LocalDate beforeMonth = date.minusMonths(5);
        CommEntity commEntity = websiteCountService.countRegister(date, beforeMonth);
        return commEntity;
    }

    /**
     * 查询用户信息
     */
    @GetMapping("/user")
    public CommEntity userInfo(@RequestHeader("Authorization") String token) {
        String userInfoToken = StringUtils.substringBetween(token, ".", ".");
        if (StringUtils.isBlank(userInfoToken)) {
            return CommEntity.create("用户解析错误,请重新尝试登录", 500);
        }
        return websiteCountService.userInfo(userInfoToken);
    }

    /**
     * 用户点赞或者取消网站
     *
     * @param isLike 0: 不喜欢我的网站,　1:喜欢我的网站
     */
    @PostMapping("/like")
    public CommEntity isLikeWebsite(@RequestParam("isLike") Integer isLike,
                                    @RequestParam("username") String username) {
        if (isLike != 1 && isLike != 0) {
            return CommEntity.create("无效的点赞请求", 500);
        }
        return websiteCountService.isLikeWebsite(isLike, username);
    }

}
