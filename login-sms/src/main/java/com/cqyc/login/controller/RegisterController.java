package com.cqyc.login.controller;

import com.cqyc.login.comm.util.StringUtilCqyc;
import com.cqyc.login.domain.CommEntity;
import com.cqyc.login.domain.vo.UserVo;
import com.cqyc.login.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-10
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private IUserService userService;

    /**
     * 判断当前账号是否被注册过
     */
    @GetMapping("/isReg/{username}")
    public CommEntity isRegisterUser(@PathVariable("username") String username) {
        CommEntity vlidStr = StringUtilCqyc.vlidStr(username);
        if (vlidStr.getCode() != 200) {
            return vlidStr;
        }
        return userService.isRegisterUser(username);
    }


    @PostMapping("/reg")
    public CommEntity register(@RequestBody @Validated UserVo user, BindingResult result) {

        if (result.hasFieldErrors()) {
            return CommEntity.create("用户信息填写不正确", 500);
        }
        CommEntity vlidStr = StringUtilCqyc.vlidStr(user.getUsername());
        if (vlidStr.getCode() != 200) {
            return vlidStr;
        }
        if (StringUtilCqyc.vlidStr(user.getPassword()).getCode() != 200) {
            return StringUtilCqyc.vlidStr(user.getPassword());
        }
        return userService.register(user);
    }

}
