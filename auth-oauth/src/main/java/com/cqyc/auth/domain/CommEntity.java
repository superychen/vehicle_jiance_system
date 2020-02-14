package com.cqyc.auth.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: cqyc
 * Description: 通用返回类型
 * Created by cqyc on 19-10-8
 */
@Getter
@Setter
public class CommEntity {
    /**
     * 表明对应请求的返回处理结果为: "200"或者其他状态码
     */
    private Integer code;

    /**
     * 若status=success, 则data内返回前端需要的json数据
     * 若status=failed, 则data内使用通用的错误码格式
     */
    private Object data;

    /**
     * 封装返回数据data
     */
    public static CommEntity create(Object data, Integer code){
        CommEntity returnType = new CommEntity();
        returnType.setCode(code);
        returnType.setData(data);
        return returnType;
    }
}
