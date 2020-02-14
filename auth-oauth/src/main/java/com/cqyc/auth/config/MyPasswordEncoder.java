package com.cqyc.auth.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: cqyc
 * Description: 自定义密码校验器
 * Created by cqyc on 20-1-13
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
//        String encode = new BCryptPasswordEncoder().encode(charSequence);
//        return encode;
        return (String) charSequence;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
//        boolean matches = new BCryptPasswordEncoder().matches(charSequence, s);
//        return matches;
        return s.equals((String) charSequence);
    }
}
