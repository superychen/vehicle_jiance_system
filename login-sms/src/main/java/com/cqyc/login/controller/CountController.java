package com.cqyc.login.controller;

import com.cqyc.login.domain.CommEntity;
import com.cqyc.login.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author: cqyc
 * Description: 统计控制层 @PreAuthorize("hasRole('ROLE_CUSTOMER')")
 * Created by cqyc on 20-1-21
 */
@RestController
@RequestMapping("count")
@Secured({"ROLE_CUSTOMER", "ROLE_ADMIN"})
public class CountController {

    @Autowired
    private CountService countService;

    @GetMapping("/register")
    public CommEntity countRegister() {
        LocalDate date = LocalDate.now();
        //统计前五个月的注册数量
        LocalDate beforeMonth = date.minusMonths(5);
        CommEntity commEntity = countService.countRegister(date, beforeMonth);
        return commEntity;
    }
}
