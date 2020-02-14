package com.cqyc.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author: cqyc
 * Description: 设置跨域访问，所有访问请求分发到网关层
 * Created by cqyc on 19-10-14
 */
//@Configuration
public class GlobalCorsConfig {
    /**
     * 配置ajax请求，设置访问路径
     */
//    @Bean
//    public CorsFilter corsFilter(){
//        //1:添加cors的配置信息
//        CorsConfiguration config = new CorsConfiguration();
//        //1:允许的域，不要写*，否则cookie就无法使用
//        config.addAllowedOrigin("http://localhost:8080");
//        //2:是否发送cookie的信息
//        config.setAllowCredentials(true);
//        //3:允许的请求方式
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("HEAD");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("DELETE");
//        config.addAllowedMethod("PATCH");
//        //4:允许的头信息
//        config.addAllowedHeader("*");
//        //5:有效时长,一小时
//        config.setMaxAge(3600L);
//
//        //添加映射路径，拦截一切请求
//        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
//        configSource.registerCorsConfiguration("/**",config);
//
//        //返回新的corsFilter
//        return new CorsFilter(configSource);
//    }
}
