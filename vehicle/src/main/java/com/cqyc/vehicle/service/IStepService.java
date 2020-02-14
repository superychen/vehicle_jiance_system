package com.cqyc.vehicle.service;

import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.domain.Step;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 汽车检测环节表 服务类
 * </p>
 *
 * @author cqyc
 * @since 2020-02-11
 */
public interface IStepService extends IService<Step> {

    /**
     * 得到全部的车辆步骤
     *
     * @return
     */
    CommEntity vehicleStep();

}
