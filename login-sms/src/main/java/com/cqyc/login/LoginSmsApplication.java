package com.cqyc.login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-24
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.cqyc.login.feign")
@EnableRedisHttpSession
@MapperScan("com.cqyc.login.mapper")
public class LoginSmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginSmsApplication.class);
    }
}
