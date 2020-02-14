package com.cqyc.vehicle.feign;

import com.cqyc.vehicle.config.security.FeignConfiguration;
import com.cqyc.vehicle.domain.JWT;
import com.cqyc.vehicle.feign.impl.AuthServiceClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
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



}
