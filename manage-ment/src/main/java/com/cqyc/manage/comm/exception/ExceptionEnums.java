package com.cqyc.manage.comm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author cqyc
 * 异常枚举类
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnums {

    /**
     * 　校验pdf时抛出的异常
     */
    PDF_CONVERSION_ERROR(405, "pdf格式出现错误,请检查pdf格式是否正确"),
    PDF_WIDTH_HEIGHT_ERROR(405,"当前图片的偏移量过大,请重新设置"),

    ;
    private int code;
    private String msg;
}
