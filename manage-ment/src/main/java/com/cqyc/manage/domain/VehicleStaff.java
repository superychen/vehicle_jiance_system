package com.cqyc.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 员工信息表
 * </p>
 *
 * @author cqyc
 * @since 2020-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VehicleStaff extends Model<VehicleStaff> {

    private static final long serialVersionUID = 1L;

    /**
     * 员工id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 员工姓名
     */
    @NotBlank(message = "员工姓名不能为空")
    private String staffName;

    /**
     * 员工年龄
     */
    @Min(18)
    @Max(55)
    private Integer staffAge;

    /**
     * 员工性别 0:表示男性  1:表示女性
     */
    @NotBlank(message = "员工性别不能为空")
    private String staffSex;

    /**
     * 员工身份证号码
     */
    @NotBlank(message = "员工身份证号码不能为空")
    @Length(min = 18, max = 18)
    private String staffIdCard;

    /**
     * 员工图片
     */
    @NotBlank(message = "员工图片不能为空")
    private String staffPic;

    /**
     * 员工简介
     */
    @NotBlank(message = "员工简介不能为空")
    private String staffIntroduce;

    /**
     * 员工工牌
     */
    private String staffNumber;

    /**
     * 员工对应用户ID
     */
    private Integer staffUserId;

    /**
     * 默认为0, 表示该职员在职,1: 表示该职员已经离职
     */
    private Integer staffStatus;

    private Integer staffRestTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
