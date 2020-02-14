package com.cqyc.vehicle.domain.vo;

import com.cqyc.vehicle.domain.Step;
import lombok.Data;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-2-12
 */
@Data
public class VehicleStepVo extends Step {

    /**
     * 1:表示合格,2表示不合格
     */
    private Integer conformity;

    /**
     * 只有为合格时有效,表示员工输入的真实合格率
     */
    private Integer isConformityText;

    /**
     * 不合格时有效,表示填写的不合格的详细说明
     */
    private String notConformtyText;

    /**
     * 不合格时有效,表示上传的不合格的图片;
     */
    private String notConformtyPic;
}
