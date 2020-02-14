package com.cqyc.login.controller;

import com.cqyc.login.comm.util.VerifyUtil;
import com.cqyc.login.domain.CommEntity;
import com.cqyc.login.service.CommService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-25
 */
@RestController
@Slf4j
@RequestMapping("comm")
public class CommController {


    @Autowired
    private CommService commService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/sms")
    public CommEntity sendSms(@RequestParam("telephone") String telephone) {
        return commService.sendSms(telephone);
    }

    /**
     * 获取验证码图片
     */
    @GetMapping("img")
    public void createImg(@RequestParam("imgUuid") String imgUuid,
                          HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            VerifyUtil randomValidateCode = new VerifyUtil();
            //输出验证码图片
            String randcode = randomValidateCode.getRandcode(request, response);
            redisTemplate.opsForHash().put("imgCode", imgUuid, randcode.toUpperCase());
            redisTemplate.expire("imgCode", 300, TimeUnit.SECONDS);
            //给图片的imgCode设置过期时间为360秒
        } catch (Exception e) {
            log.error("获取验证码异常：", e);
            throw new RuntimeException("生成随机验证码出现异常");
        }
    }


}
