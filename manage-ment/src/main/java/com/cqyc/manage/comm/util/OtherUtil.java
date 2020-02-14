package com.cqyc.manage.comm.util;

import java.util.Random;
import java.util.UUID;

/**
 * @author: cqyc
 * Description: 一些小工具类
 * Created by cqyc on 19-12-23
 */
public class OtherUtil {

    /**
     * 生成uuid
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static String sixRandom() {
//        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numberStr = "0123456789";
        Random random1 = new Random();
        //指定字符串长度，拼接字符并toString
        StringBuffer sb = new StringBuffer();
        //前两个字符
        for (int i = 0; i < 2; i++) {
            //获取指定长度的字符串中任意一个字符的索引值
            int number = random1.nextInt(str.length());
            //根据索引值获取对应的字符
            char charAt = str.charAt(number);
            sb.append(charAt);
        }
        //后面4个字符
        for (int i = 0; i < 4; i++) {
            //获取指定长度的字符串中任意一个字符的索引值
            int number = random1.nextInt(numberStr.length());
            //根据索引值获取对应的字符
            char charAt = numberStr.charAt(number);
            sb.append(charAt);
        }
        return sb.toString();
    }


}
