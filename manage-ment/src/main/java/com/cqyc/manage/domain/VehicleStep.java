package com.cqyc.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 汽车检测环节表
 * </p>
 *
 * @author cqyc
 * @since 2020-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VehicleStep extends Model<VehicleStep> {

    private static final long serialVersionUID = 1L;

    /**
     * 环节id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 汽车环节题目
     */
    @NotBlank(message = "环节标题不能为空")
    private String stepTitle;

    /**
     * 环节指标，百分比数目，１００为最大，０为最小
     */
    @Min(value = 30, message = "环节指标率不能小于30%")
    @Max(value = 100, message = "环节指标率不能大于100%")
    private Integer stepIndicator;

    /**
     * 环节描述
     */
    @NotBlank(message = "环节描述不能为空")
    private String stepIntroduce;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
