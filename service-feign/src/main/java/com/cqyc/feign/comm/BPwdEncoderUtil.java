package com.cqyc.feign.comm;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author: cqyc
 * Description: 配置用于用户密码 加密 的工具类 BPwdEncoderUtil
 * Created by cqyc on 20-1-14
 */
public class BPwdEncoderUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String BCryptPassword(String password){
        return encoder.encode(password);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword){
        return encoder.matches(rawPassword,encodedPassword);
    }
}
