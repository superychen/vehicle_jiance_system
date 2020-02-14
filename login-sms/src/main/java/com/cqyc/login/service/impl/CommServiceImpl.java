package com.cqyc.login.service.impl;

import com.cqyc.login.comm.util.SmsUtil;
import com.cqyc.login.comm.util.ValidUtils;
import com.cqyc.login.config.rabbitmq.MQConfig;
import com.cqyc.login.domain.CommEntity;
import com.cqyc.login.service.CommService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: cqyc
 * Description: 实现层
 * Created by cqyc on 20-1-19
 */
@Service
@Slf4j
public class CommServiceImpl implements CommService {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public CommEntity sendSms(String telephone) {
        String code = SmsUtil.newCode();
        HashMap<String, String> map = new HashMap<>();
        map.put("telephone", telephone);
        map.put("code", code);
        if (!ValidUtils.isMobile(telephone)) {
            return CommEntity.create("输入的手机号有误,请仔细检查！！！", 500);
        }
        if (redisTemplate.hasKey(telephone + ":phoneCode") != null) {
            Long expire = redisTemplate.getExpire(telephone + ":phoneCode");
            if (expire != null && expire > 240) {
                return CommEntity.create("距离上次发送短信验证码时间过短", 500);
            }
        }
        //讲短信验证码存入到redis中,并且设置过期时间为300秒/5分钟
        redisTemplate.opsForValue().set(telephone + ":phoneCode", code, 300, TimeUnit.SECONDS);
        rabbitTemplate.convertAndSend(MQConfig.SEND_MESSAGE_EXCHANGE, MQConfig.SEND_MESSAGE_ROUTING_KEY1, map);
        return CommEntity.create("发送短信成功", 200);
    }
}
