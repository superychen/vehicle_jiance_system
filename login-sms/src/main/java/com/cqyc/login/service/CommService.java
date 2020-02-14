package com.cqyc.login.service;

import com.cqyc.login.domain.CommEntity;

/**
 * @author: cqyc
 * Description: 短信验证码一类的业务层
 * Created by cqyc on 20-1-19
 */
public interface CommService {
    CommEntity sendSms(String telephone);

}
