package com.cqyc.vehicle.service;

import com.cqyc.vehicle.domain.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.domain.User;
import com.cqyc.vehicle.domain.vo.DistriVo;

/**
 * <p>
 * 车辆预约表 服务类
 * </p>
 *
 * @author cqyc
 * @since 2020-01-31
 */
public interface IAppointmentService extends IService<Appointment> {

    /**
     * 预约状态
     *
     * @param uid
     * @return comm
     */
    CommEntity appointStatus(Integer uid);

    /**
     * 得到所有的预约信息(只包括未被检测的)
     *
     * @param pageNo   当前页
     * @param pageSize 个数
     * @return comm
     */
    CommEntity getAppoints(Integer pageNo, Integer pageSize);

    /**
     * 分配员工
     *
     * @param distriVo 预约Id和员工id
     * @return comm
     */
    CommEntity addDistributionStaff(DistriVo distriVo);

    /**
     * 员工得到对应的预约车辆信息
     *
     * @param user 用户类
     * @return
     */
    CommEntity getAppointStaff(User user);

}
