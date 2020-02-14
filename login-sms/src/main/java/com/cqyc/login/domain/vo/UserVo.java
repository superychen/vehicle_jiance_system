package com.cqyc.login.domain.vo;

import com.cqyc.login.domain.JWT;
import com.cqyc.login.domain.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-19
 */
@Data
public class UserVo extends User implements Serializable {

    @NotBlank(message = "电话验证码不能为空")
    private String telephoneCode;

    private JWT jwt;
}
