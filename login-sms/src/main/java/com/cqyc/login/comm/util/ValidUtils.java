package com.cqyc.login.comm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: cqyc
 * Description: 校验手机邮箱的工具类
 * Created by cqyc on 19-10-25
 */
public class ValidUtils {

    /**
     * 手机号验证制定规则
     */
    private final static String PHONE_REGEX1 = "^[1][3,4,5,7,8][0-9]{9}$";
    private final static String PHONE_REGEX2 = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$";
    private final static String EMAI_REGEX1 = "[a-zA-Z]+[a-zA-Z0-9_]*@[a-zA-Z0-9]+[.][a-zA-Z0-9]+";
    private final static String EMAI_REGEX2 = "[a-zA-Z]+[a-zA-Z0-9_]*@[a-zA-Z0-9]+[.][a-zA-Z0-9]+[.][a-zA-Z0-9]+";
    private final static String IS_CONTAIN_CHINESE = "[\u4e00-\u9fa5]";

    /**
     * 验证手机格式是否正确
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean isMatch = false;

        p = Pattern.compile(PHONE_REGEX2);
        m = p.matcher(str);
        isMatch = m.matches();
        return isMatch;
    }

    /**
     * 判断当前的字符串是否有中文汉字
     * @param countname 输入
     * @return true/false
     */
    public static boolean checkIsContainChiese(String countname) {
        Pattern pattern = Pattern.compile(IS_CONTAIN_CHINESE);
        Matcher m = pattern.matcher(countname);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 输入的格式是否为email
     *
     * @param email email
     * @return true/false
     */
    public static boolean checkEmail(String email) {
        //查找邮箱
        //mailRegex是整体邮箱正则表达式，mailName是@前面的名称部分，mailDomain是后面的域名部分
        String mailRegex, mailName, mailDomain;
        //^表明一行以什么开头；^[0-9a-z]表明要以数字或小写字母开头；\\w*表明匹配任意个大写小写字母或数字或下划线
        mailName = "^[0-9a-z]+\\w*";
        //***.***.***格式的域名，其中*为小写字母或数字;第一个括号代表有至少一个***.
        // 匹配单元，而[0-9a-z]$表明以小写字母或数字结尾
        mailDomain = "([0-9a-z]+\\.)+[0-9a-z]+$";
        //邮箱正则表达式      ^[0-9a-z]+\w*@([0-9a-z]+\.)+[0-9a-z]+$
        mailRegex = mailName + "@" + mailDomain;
        Pattern pattern = Pattern.compile(mailRegex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
