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
 * 汽车最终检测结果表
 * </p>
 *
 * @author cqyc
 * @since 2020-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vehicle_result")
public class Result extends Model<Result> {

    private static final long serialVersionUID = 1L;

    /**
     * 汽车检测结果信息表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 结果出来的时间
     */
    private LocalDateTime resultTime;

    /**
     * 对应的预约信息车辆表中的id,找到对应的车辆
     */
    private Integer resultAppointId;

    /**
     * 检测结果,全部为json格式,key为检测的标题,value为结果
     */
    private String resultContent;

    /**
     * 结果对应的用户id
     */
    private Integer resultUserId;

    @TableField(exist = false)
    private Appointment appointment;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
