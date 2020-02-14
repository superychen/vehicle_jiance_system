package com.cqyc.login.config.rabbitmq;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.cqyc.login.comm.util.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author cqyc
 * 异步操作
 */
@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private SmsUtil smsUtil;


    /**
     * 监听发送消息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MQConfig.SEND_MESSAGE_QUEUE, durable = "true"),
            exchange = @Exchange(name = MQConfig.SEND_MESSAGE_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {MQConfig.SEND_MESSAGE_ROUTING_KEY1}
    ))
    public void litenSendMessage(Map<String, String> map) {
        String code = map.get("code");
        String telephone = map.get("telephone");
        log.info("mq监听到的消息参数---telephone---＞{} ---code---{}", telephone, code);
        SendSmsResponse sendSmsResponse = smsUtil.sendSms(telephone, code);
        log.info("发送短信结果--->{}", sendSmsResponse.getMessage());
    }

//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(Map<String, String> map) {
//        log.info("receive message:" + map);
//        String code = map.get("code");
//        String telephone = map.get("telephone");
//        log.info("mq监听到的消息参数---telephone---＞{} ---code---{}", telephone, code);
//        SendSmsResponse sendSmsResponse = smsUtil.sendSms(telephone, code);
//        log.info("发送短信结果--->{}", sendSmsResponse.getMessage());
//    }

}
