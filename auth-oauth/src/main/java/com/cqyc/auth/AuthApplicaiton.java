package com.cqyc.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-12
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.cqyc.auth.mapper")
public class AuthApplicaiton {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplicaiton.class);
    }
}
