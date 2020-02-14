package com.cqyc.feign.controller;

import com.cqyc.feign.feign.SchedualServiceHi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-12
 */
@RestController
@RequestMapping("/feign")
@Slf4j
public class FeignController {

    @Autowired
    private SchedualServiceHi serviceHi;

    @Value("${foo}")
    private String foo;

    @GetMapping("/hi")
    public String sayHi(@RequestParam("name") String name){
        log.info("从配置文件里面读取的内容--->{}",foo);
        return serviceHi.home(name);
    }

}
