package com.cqyc.login.feign;

import com.cqyc.login.config.security.FeignConfiguration;
import com.cqyc.login.domain.JWT;
import com.cqyc.login.feign.impl.AuthServiceClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-14
 */
@FeignClient(value = "auth-oauth", configuration = FeignConfiguration.class, fallback = AuthServiceClientImpl.class)
public interface AuthServiceClient {

    /**
     * 调用接口
     *
     * @param authorization
     * @param grant_type
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/oauth/token")
    JWT postAccessToken(@RequestHeader("Authorization") String authorization,
                        @RequestParam("grant_type") String grant_type,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password);


    @GetMapping("/api/test")
    String testMessage(@RequestParam("test_name") String test_name);

}
