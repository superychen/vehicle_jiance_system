package com.cqyc.feign.feign.impl;

import com.cqyc.feign.feign.SchedualServiceHi;
import org.springframework.stereotype.Component;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-12
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String home(String name) {
        return "服务出现了异常,请及时联系管理员";
    }
}
