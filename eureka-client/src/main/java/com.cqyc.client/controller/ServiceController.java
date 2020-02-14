package com.cqyc.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-11
 */
@RestController
public class ServiceController {

    @GetMapping("/hi")
    public String home(@RequestParam("name") String name){
        return name+" hello";
    }
}
