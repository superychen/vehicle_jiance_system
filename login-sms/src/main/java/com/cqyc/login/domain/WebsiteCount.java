package com.cqyc.login.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDate;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 网站统计表
 * </p>
 *
 * @author cqyc
 * @since 2020-01-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WebsiteCount extends Model<WebsiteCount> {

    private static final long serialVersionUID = 1L;

    /**
     * 网站统计id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户注册辆，每月为单位
     */
    private Integer userRegisterCount;

    /**
     * 网站访问量,每月为单位
     */
    private Integer websiteLook;

    /**
     * 网站车辆预约数量，以每月为单位
     */
    private Integer websiteVehicleAppoint;

    /**
     * 网站点赞数量,以每月为单位
     */
    private Integer websiteLikedCount;

    /**
     * 网站统计的月份
     */
    private LocalDate websiteMonth;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
