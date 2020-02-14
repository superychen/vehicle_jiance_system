package com.cqyc.login.config.rabbitmq;

import com.cqyc.login.domain.User;
import com.cqyc.login.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-23
 */
@Component
public class MQReceiver2 {
    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private UserMapper userMapper;

    /**
     * 监听点赞,讲点赞异步写入数据库
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MQConfig.LIKE_MESSAGE_QUEUE, durable = "true"),
            exchange = @Exchange(name = MQConfig.SEND_MESSAGE_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {MQConfig.SEND_MESSAGE_ROUTING_KEY2}
    ))
    public void litenSendLike(Map<String, Integer> map) {
        Integer uid = map.get("uid");
        Integer isLike = map.get("isLike");
        User user = new User();
        user.setUid(uid);
        user.setLikeWebsite(isLike);
        int i = userMapper.updateById(user);
        log.info("修改用户点赞结果----->{}", i);
    }


}
