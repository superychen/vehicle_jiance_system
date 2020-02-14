package com.cqyc.login.service;

import com.cqyc.login.domain.CommEntity;

import java.time.LocalDate;

/**
 * @author: cqyc
 * Description: 统计接口实现层
 * Created by cqyc on 20-1-21
 */
public interface CountService {

    /**
     * 统计五个月前到现在的注册用户数量
     *
     * @param date        　日期
     * @param beforeMonth 　当期日期
     * @return
     */
    CommEntity countRegister(LocalDate date, LocalDate beforeMonth);
}
