package com.cqyc.manage.feign;

import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.feign.impl.SmsFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: cqyc
 * Description: 调用发送短信
 * Created by cqyc on 19-11-5
 */
@FeignClient(value = "login-sms", fallback = SmsFeignImpl.class)
public interface SmsFeign {

    /**
     * 调用发送短信借口
     *
     * @param telephone 手机号码
     * @return commEntity
     */
    @PostMapping("/comm/sms")
    CommEntity testSms(@RequestParam("telephone") String telephone);

}
