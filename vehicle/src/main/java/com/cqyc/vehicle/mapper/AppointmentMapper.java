package com.cqyc.vehicle.mapper;

import com.cqyc.vehicle.domain.Appointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 车辆预约表 Mapper 接口
 * </p>
 *
 * @author cqyc
 * @since 2020-01-31
 */
public interface AppointmentMapper extends BaseMapper<Appointment> {

    /**
     * 查询订单表对应的数据
     *
     * @param uid
     * @return
     */
    List<Appointment> appointStatus(@Param("uid") Integer uid);

    /**
     * 得到员工需要检测的车辆信息
     *
     * @param appointIds
     * @return
     */
    List<Appointment> appointStaff(@Param("appointIds") List<Integer> appointIds);


    /**
     * 查询单个车辆预约信息
     *
     * @param appId 预约id
     * @return
     */
    Appointment oneAppointInfo(@Param("appId") Integer appId);
}
