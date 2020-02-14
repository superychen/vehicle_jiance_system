package com.cqyc.manage.feign.impl;

import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.feign.SmsFeign;
import org.springframework.stereotype.Component;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-11-5
 */
@Component
public class SmsFeignImpl implements SmsFeign {

    @Override
    public CommEntity testSms(String telephone) {
        return CommEntity.create("发送短信出现异常,请稍后再次尝试", 500);
    }

}
