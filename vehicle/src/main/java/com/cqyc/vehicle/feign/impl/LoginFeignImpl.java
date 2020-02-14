package com.cqyc.vehicle.feign.impl;

import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.feign.LoginFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-29
 */
@Component
@Slf4j
public class LoginFeignImpl implements LoginFeign {

    @Override
    public CommEntity userInfo(String token) {
        log.error("进入了熔断.....");
        return null;
    }
}
