package com.cqyc.manage.controller;


import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.User;
import com.cqyc.manage.domain.vo.UserVo;
import com.cqyc.manage.feign.SmsFeign;
import com.cqyc.manage.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2019-10-14
 */
@RestController
@RequestMapping("/manage")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private SmsFeign smsFeign;


    /**
     * 查询所有的用户
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity allUser(@RequestParam(value = "username", required = false) String username,
                              @RequestParam("pageNo") Integer pageNo,
                              @RequestParam("pageSize") Integer pageSize) {
        return userService.allUser(username, pageNo, pageSize);
    }

    /**
     * 查询单个用户
     */
    @GetMapping("/user/{uid}")
    public CommEntity oneUser(@PathVariable("uid") Integer uid) {
        return userService.oneUser(uid);
    }


    /**
     * 新增用户
     */
    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity addUser(@RequestBody @Validated UserVo user, BindingResult result) {
        if (result.hasFieldErrors()) {
            log.info("错误原因--->{}", result.getFieldErrors());
            return CommEntity.create("输入的内容不能为空", 500);
        }
        return userService.addUser(user);
    }

    /**
     * 修改用户
     */
    @PutMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity updateUser(@RequestBody @Validated UserVo user, BindingResult result) {
        if (result.hasFieldErrors()) {
            return CommEntity.create("输入的内容不能为空", 500);
        }
        return userService.updateUser(user);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity deleteUser(@RequestBody Integer[] ids) {
        if (ids == null) {
            return CommEntity.create("用户删除获取的id不能为空", 500);
        }
        return userService.deleteUser(ids);
    }


    @PostMapping("sendsms")
    public CommEntity sendsms(@RequestParam("telephone") String telephone) {
        return userService.sendSms(telephone);
    }
}
