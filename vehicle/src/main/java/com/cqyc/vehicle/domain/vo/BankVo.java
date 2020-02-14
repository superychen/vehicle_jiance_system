package com.cqyc.vehicle.domain.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-27
 */
@Data
public class BankVo {
    @NotBlank(message = "银行卡姓名不能为空")
    private String bankName;

    @Length(min = 18, max = 18, message = "身份证号码格式不正确")
    private String bankCardNum;

    @NotBlank(message = "银行卡不能为空")
    private String bankNum;

    @NotBlank(message = "手机号码不能为空")
    @Length(min = 11, max = 11)
    private String phoneNum;

    @NotBlank(message = "验证码不能为空")
    private String phoneCode;
}
