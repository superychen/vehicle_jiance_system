package com.cqyc.login.comm.util;

import java.util.UUID;

/**
 * @author: cqyc
 * Description: 加密或者处理唯一键
 * Created by cqyc on 19-10-27
 */
public class Md5Hash {
    public static String createUUid() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }
}
