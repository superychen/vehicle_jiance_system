package com.cqyc.login.comm.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cqyc.login.comm.propties.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author: cqyc
 * Description: 发送短信工具类
 * Created by cqyc on 19-10-24
 */
@Slf4j
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsUtil {

    @Autowired
    private SmsProperties sms;
//    private static final String product = "Dysmsapi";
//
//    private static final String domain = "dysmsapi.aliyuncs.com";
//
//    private static final String accessKeyId = "LTAI4FfpGEipkM6WzzNeZdo7";
//    private static final String accessKeySecret = "7yN6dFNu064ZkQpzqXN7ahzfvumYV3";
//    private final static String SIGN_NAME = "paunchy";
//    private final static String TEMPLATE_CODE = "SMS_166486367";

    /**
     * 生成4位的随机验证码
     */
    public static String newCode() {
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if (randLength < 4) {
            for (int i = 1; i <= 4 - randLength; i++) {
                fourRandom = "0" + fourRandom;
            }
        }
        return fourRandom;
    }

    /**
     * 发送验证码
     */
    public SendSmsResponse sendSms(String telephone, String code) {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", sms.getAccessKeyId(), sms.getAccessKeySecret());
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", sms.getProduct(), sms.getDomain());
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("发送短信过程中出现了不可预测的异常");
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(telephone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(sms.getSignName());
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(sms.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
///        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
///        request.setOutId("yourOutId");
        //hint 此处可能会抛出异常，注意catch
        try {
            String smsRes = "OK";
            String errorCode = "isv.BUSINESS_LIMIT_CONTROL";
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals(smsRes)) {
                log.info("短信发送成功");
            } else if (StringUtils.equals(errorCode, sendSmsResponse.getCode())) {
                log.info("短信发送失败,错误code为 ----> {}", sendSmsResponse.getCode());
                sendSmsResponse.setMessage("发送短信频率太高了,请稍后再次发送");
            }
            return sendSmsResponse;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
