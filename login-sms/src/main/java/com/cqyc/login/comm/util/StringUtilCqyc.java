package com.cqyc.login.comm.util;

import com.cqyc.login.domain.CommEntity;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-10
 */
public class StringUtilCqyc {

    private final static String IS_CHINESE = "[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]";

    /**
     * 校验当前的字符串
     *
     * @return
     */
    public static CommEntity vlidStr(String str) {
        //判断当前页面是否包含英文空格还是中文空格
        if (StringUtils.contains(str, " ") || StringUtils.contains(str, "　")) {
            return CommEntity.create("不能包含空格", 500);
        } else if (isContainChinese(str)) {
            return CommEntity.create("输入的格式不能包含中文符号", 500);
        } else if (StringUtils.isBlank(str) || StringUtils.length(str) < 6 || StringUtils.length(str) > 32) {
            return CommEntity.create("输入格式不正确", 500);
        }
        return CommEntity.create(true, 200);
    }


    public static boolean isContainChinese(String str) {
        try {
            if (StringUtils.isEmpty(str)) {
                throw new NullPointerException("输入的用户名为空");
            }
            Pattern p = Pattern.compile(IS_CHINESE);
            Matcher m = p.matcher(str);
            if (m.find()) {
                return true;
            }
            return false;
        } catch (NullPointerException e) {
            return false;
        }

    }
}
