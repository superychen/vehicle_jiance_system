package com.cqyc.feign.domain;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

/**
 * @author: cqyc
 * Description: UserLoginDTO 包含了一个 User 和一个 JWT 成员属性，用于返回数据的实体：
 * Created by cqyc on 20-1-14
 */
@Data
public class UserLoginDTO {
    private JWT jwt;
    private User user;
}
