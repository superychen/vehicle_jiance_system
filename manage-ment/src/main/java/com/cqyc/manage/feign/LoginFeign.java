package com.cqyc.manage.feign;

import com.cqyc.manage.config.security.FeignConfiguration;
import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.feign.impl.LoginFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-29
 */
@FeignClient(value = "login-sms", configuration = FeignConfiguration.class, fallback = LoginFeignImpl.class)
public interface LoginFeign {

    /**
     * 用户详情
     *
     * @param token 请求头中的token
     * @return
     */
    @GetMapping("/website/user")
    CommEntity userInfo(@RequestHeader("Authorization") String token);
}
