package com.cqyc.vehicle.domain;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 汽车检测环节表
    * </p>
*
* @author cqyc
* @since 2020-02-11
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("vehicle_step")
    public class Step extends Model<Step> {

    private static final long serialVersionUID = 1L;

            /**
            * 环节id

            */
            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

            /**
            * 汽车环节题目
            */
    private String stepTitle;

            /**
            * 环节指标，百分比数目，１００为最大，０为最小
            */
    private Integer stepIndicator;

            /**
            * 环节描述
            */
    private String stepIntroduce;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
