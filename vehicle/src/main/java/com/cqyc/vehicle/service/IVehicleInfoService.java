package com.cqyc.vehicle.service;

import com.cqyc.vehicle.domain.Appointment;
import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.domain.VehicleInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cqyc.vehicle.domain.vo.BankVo;
import com.cqyc.vehicle.domain.vo.VehicleInfoVo;

/**
 * <p>
 * 车辆信息表 服务类
 * </p>
 *
 * @author cqyc
 * @since 2020-01-27
 */
public interface IVehicleInfoService extends IService<VehicleInfo> {

    /**
     * 校验银行卡４要素认证
     *
     * @param bankVo
     * @return
     */
    CommEntity verityBank(BankVo bankVo);

    /**
     * 插入车辆信息
     *
     * @param vehicleInfoVo 车辆信息
     * @param token         用户的token
     * @return
     */
    CommEntity vehicleInfo(VehicleInfoVo vehicleInfoVo, String token);

    /**
     * 判断当前用户是否以前录入过车辆信息
     *
     * @param token
     * @return
     */
    CommEntity isVehicle(String token);

    /**
     * 预约
     *
     * @param appointment
     * @return
     */
    CommEntity appointVehicle(Appointment appointment);
}
