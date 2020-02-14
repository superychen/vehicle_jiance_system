package com.cqyc.vehicle.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 车辆预约表
 * </p>
 *
 * @author cqyc
 * @since 2020-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vehicle_appointment")
public class Appointment extends Model<Appointment> {

    private static final long serialVersionUID = 1L;

    /**
     * 车辆预约id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 车辆信息id
     */
    private Integer vehicleInfoId;

    /**
     * 预约时间，根据预约时间之内去拖车
     */
    private LocalDateTime appointTime;

    /**
     * 预约地址，技术如果如果需要地址
     * 信息
     */
    private String appointAddress;

    /**
     * 预约人姓名
     */
    private String appointUsername;

    /**
     * 0:表示没有支付,1:表示已支付
     */
    private Integer appointPay;

    /**
     * 预约是否被检测,0:未检测,1:检测中,2:已检测
     */
    private Integer appointIsDetection;

    private String appointStaff;

    @TableField(exist = false)
    private VehicleInfo vehicleInfo;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
