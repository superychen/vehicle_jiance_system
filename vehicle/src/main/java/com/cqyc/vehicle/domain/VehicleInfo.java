package com.cqyc.vehicle.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 车辆信息表
 * </p>
 *
 * @author cqyc
 * @since 2020-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vehicle_info")
public class VehicleInfo extends Model<VehicleInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 车辆信息ｉｄ标识，不参与任何
     * 业务
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 车辆管理单位
     */
    @NotBlank(message = "车辆管理单位不能为空")
    private String vehicleManagement;

    /**
     * 车辆的档案编号
     */
    @NotBlank(message = "车辆图片不能为空")
    private String fileNumber;

    /**
     * 车辆使用人
     */
    @NotBlank(message = "车主姓名不能为空")
    private String vehicleUser;

    /**
     * 车辆车主的电话,我们会根据电话联系该车主进行车辆检测
     */
    private String vehicleUserTelephone;

    /**
     * 车牌号码
     */
    @NotBlank(message = "车牌号码不能为空")
    private String vehicleNumber;

    /**
     * 车辆车主的身份证号
     */
    private String vehicleUserIdNumber;

    /**
     * 车辆类型
     */
    @NotBlank(message = "车辆类型不能为空")
    private String vehicleType;

    /**
     * 车辆颜色
     */
    @NotBlank(message = "车辆颜色不能为空")
    private String vehicleColor;

    /**
     * 车辆品牌型号
     */
    private String systemType;

    /**
     * 车辆信息填写日期
     */
    private LocalDate vehicleDate;

    /**
     * 车辆所属人id,对应用户表
     */
    private Integer vehicleUserId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
