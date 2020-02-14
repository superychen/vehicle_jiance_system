package com.cqyc.ribbon.controller;

import com.cqyc.ribbon.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("ribbon")
public class RibbonController {

    @Autowired
    private RibbonService ribbonService;

    @GetMapping("/hi")
    public String ribbonHi(@RequestParam("name") String name){
        return ribbonService.ribbonHi(name);
    }


}
