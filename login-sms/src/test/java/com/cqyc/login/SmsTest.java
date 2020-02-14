package com.cqyc.login;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.cqyc.login.comm.util.SmsUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-24
 */
public class SmsTest {
    //以下为测试代码，随机生成验证码
    private static int newcode;

    public static int getNewcode() {
        return newcode;
    }

    public static void setNewcode() {
        newcode = (int) (Math.random() * 9999) + 100;  //每次调用生成一位四位数的随机数
    }

    public static void main(String[] args) throws ClientException, InterruptedException {
        String code = SmsUtil.newCode();
        System.out.println("发送的验证码为：" + code);
        //发短信
//        SendSmsResponse response = SmsUtil.sendSms("13022326792", code);
//        System.out.println("短信接口返回的数据----------------");
//        System.out.println("Code=" + response.getCode());
//        System.out.println("Message=" + response.getMessage());
//        System.out.println("RequestId=" + response.getRequestId());
//        System.out.println("BizId=" + response.getBizId());

       /* 不删 留着 以后可能有用
        System.out.println("  ==============================================  ");
        Thread.sleep(3000L);
        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                System.out.println("SmsSendDetailDTO["+i+"]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }*/
    }


    @Test
    public void test() {
        //2 * 2 ^ 3
//        System.out.println(16 >> 2);
//        System.out.println(2 << 4);
        String res = "nsdfasdf";
        String s = StringUtils.remove(res, " ");
        String remove = StringUtils.remove(s, "　");
//        String s = StringUtils.substringBetween(res, "/");
        System.out.println("s = " + s);
        System.out.println("remove = " + remove);
    }

    @Test
    public void test1() {
//        System.out.println(LocalDate.now().minusMonths(5).toString());
        String str = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Nzk4NDcxNzksInVzZXJfbmFtZSI6ImNxeWMiLCJhdXRob3JpdGllcyI6WyJST0xFX0NVU1RPTUVSIiwiUk9MRV9BRE1JTiIsIi9sb2dpbiIsImRlbGV0ZSIsImVkaXQiLCJxdWVyeSJdLCJqdGkiOiI2M2NjZGRkZC04NmQwLTQxNGUtYTM1NC02NTMxNDBjNDIwYmYiLCJjbGllbnRfaWQiOiJjcXljIiwic2NvcGUiOlsiYWxsIl19.fGJEPY9V_WRflsZl2G6o40IBFGtIyLlMi278zuC3wU6q5PaULlsz_jcl7IhYqJQvK3RU3ALI3T_TD8GEfS9Oqt1F6VKe7cX1ukf5Wu0KgPsoqOEexQE6L04Ke-JclNyYxfnWQsLD4_h2GbU-LtQGfmVTOGIMfd_e5970MVOaEmg9b9PCQelE7IWt1030HMBz0rFCfr8hheZ__-kCDHbGj9mSyjoiyBxklZ-OfBhKOQYAw-rn1nUb7LsVKTsU9gF5_ZXZDRv1Y5c-5o9BmgTbWKXNFkT2U9bLnKLxk0lDKQA6XbBAuZDx1faIfWc68VGD-EpptxwAqHAQh1vFnOIzIQ";
        String s = StringUtils.substringBetween(str, ".", ".");
        System.out.println("s = " + s);
        //解码
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            String res = new String(decoder.decodeBuffer(s));
            System.out.println(res);
            JSONObject jsonObject = JSONObject.parseObject(res);
            String user_name = jsonObject.getString("user_name");
            System.out.println("user_name = " + user_name);
        } catch (IOException e) {
            System.out.println("出错了");
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        LocalDateTime localDateTime = LocalDateTime.now().minusYears(2);
        System.out.println("localDateTime = " + localDateTime);
    }

}
