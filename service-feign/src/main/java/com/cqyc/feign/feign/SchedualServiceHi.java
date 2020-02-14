package com.cqyc.feign.feign;

import com.cqyc.feign.feign.impl.SchedualServiceHiHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-12
 */
@FeignClient(value = "SERVICE-DEMO",fallback = SchedualServiceHiHystric.class)
public interface SchedualServiceHi {

    @GetMapping("/hi")
    String home(@RequestParam("name") String name);
}
