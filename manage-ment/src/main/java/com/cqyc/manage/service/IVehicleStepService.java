package com.cqyc.manage.service;

import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.VehicleStep;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 汽车检测环节表 服务类
 * </p>
 *
 * @author cqyc
 * @since 2020-02-07
 */
public interface IVehicleStepService extends IService<VehicleStep> {

    /**
     * 新增环节
     *
     * @param vehicleStep
     * @return
     */
    CommEntity addStep(VehicleStep vehicleStep);

    /**
     * 得到所有的环节
     *
     * @param pageNo 当前页
     * @param pageSize 页数
     * @return com
     */
    CommEntity getSteps(Integer pageNo, Integer pageSize);

    /**
     * 修改环节
     * @param vehicleStep
     * @return
     */
    CommEntity updateStep(VehicleStep vehicleStep);

}
