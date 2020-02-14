package com.cqyc.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-11
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceHiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHiApplication.class);
    }
}
