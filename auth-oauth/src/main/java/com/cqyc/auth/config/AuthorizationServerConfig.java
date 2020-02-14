package com.cqyc.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.*;


/**
 * @author: cqyc
 * Description:认证服务器,开启授权服务,需要对外暴露获取和验证 Token 的接口，所以也是一个资源服务
 * Created by cqyc on 20-1-12
 */
@Configuration
@EnableAuthorizationServer
//@EnableResourceServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 以秒为单位,过期时间设置为24小时
     */
    private final static Integer TIME_OUT = 60 * 60 * 24;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // 将客户端的信息存储在内存中
                // 客户端
                .withClient("cqyc")
                // 客户端密码
                .secret("123456")
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                // 设置 token 过期时间,半个小时过期
                .accessTokenValiditySeconds(TIME_OUT)
                .scopes("all");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // token 的存储方式
        endpoints.tokenStore(tokenStore())
                // 开启密码验证，来源于 WebSecurityConfigurerAdapter
                .authenticationManager(authenticationManager)
//                .userDetailsService(userServiceDetail); // 读取验证用户的信息
                .tokenEnhancer(jwtTokenEnhancer());
    }


    // JWT
    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        // 配置jks文件
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("spring-jwt.jks"), "abc123".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("spring-jwt"));
        return converter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        // 获取密钥需要身份认证，使用单点登录时必须配置
        security.tokenKeyAccess("isAuthenticated()");
        security.passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
