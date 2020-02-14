package com.cqyc.login.comm.propties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-25
 */
@Data
@ConfigurationProperties(prefix = "cqyc.sms")
public class SmsProperties {
    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    private String product;
    /**
     * 产品域名,开发者无需替换
     */
    private String domain;
    /**
     * 生成的私钥key
     */
    private String accessKeyId;
    /**
     * 私钥
     */
    private String accessKeySecret;
    /**
     * 签名名称
     */
    private String signName;
    /**
     * 模板code
     */
    private String templateCode;
}
