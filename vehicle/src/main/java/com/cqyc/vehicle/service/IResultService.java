package com.cqyc.vehicle.service;

import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.domain.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cqyc.vehicle.domain.User;
import com.cqyc.vehicle.domain.bo.VehicleAllResBo;

import java.util.List;

/**
 * <p>
 * 汽车最终检测结果表 服务类
 * </p>
 *
 * @author cqyc
 * @since 2020-02-12
 */
public interface IResultService extends IService<Result> {

    /**
     * 员工上传车辆检测结果
     *
     * @param result 实体类
     * @return
     */
    CommEntity addVehicleResult(Result result);

    /**
     * 得到当前用户的检查的车辆信息结果
     *
     * @param uid      用户id
     * @param pageNo   当前页
     * @param pageSize 每页的页数
     * @return comm
     */
    CommEntity getResults(Integer uid, Integer pageNo, Integer pageSize);

    /**
     * 导出全部的excel
     *
     * @param user 用户的token
     * @return
     */
    List<VehicleAllResBo> exportAllReport(User user);

}
