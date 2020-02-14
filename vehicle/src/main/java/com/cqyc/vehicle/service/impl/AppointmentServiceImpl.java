package com.cqyc.vehicle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqyc.vehicle.domain.Appointment;
import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.domain.User;
import com.cqyc.vehicle.domain.VehicleInfo;
import com.cqyc.vehicle.domain.vo.DistriVo;
import com.cqyc.vehicle.feign.ManageFeign;
import com.cqyc.vehicle.mapper.AppointmentMapper;
import com.cqyc.vehicle.mapper.VehicleInfoMapper;
import com.cqyc.vehicle.service.IAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 车辆预约表 服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2020-01-31
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements IAppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private VehicleInfoMapper vehicleInfoMapper;

    @Autowired
    private ManageFeign manageFeign;


    @Override
    public CommEntity appointStatus(Integer uid) {
        List<Appointment> appointments = appointmentMapper.appointStatus(uid);
        if (CollectionUtils.isEmpty(appointments)) {
            //返回状态值,表示没有任何数据
            return CommEntity.create("您还没有任何数据", 10086);
        }
        return CommEntity.create(appointments, 200);
    }

    @Override
    public CommEntity getAppoints(Integer pageNo, Integer pageSize) {
        LambdaQueryWrapper<Appointment> wrapper = new QueryWrapper<Appointment>().lambda().eq(Appointment::getAppointPay, 1).eq(Appointment::getAppointIsDetection, 0);
        Page<Appointment> appointmentPage = new Page<>(pageNo, pageSize);
        //查询已经支付的,并且还没有被检测的数据
        IPage<Appointment> appointmentIPage = appointmentMapper.selectPage(appointmentPage, wrapper);
        if (CollectionUtils.isEmpty(appointmentIPage.getRecords())) {
            return CommEntity.create("目前还没有被预约的项目哟!!!", 203);
        }
        appointmentIPage.getRecords().forEach(res -> {
            VehicleInfo vehicleInfo = vehicleInfoMapper.selectById(res.getVehicleInfoId());
            res.setVehicleInfo(vehicleInfo);
        });
        return CommEntity.create(appointmentIPage, 200);
    }

    /**
     * 分配员工
     */
    @Override
    public CommEntity addDistributionStaff(DistriVo distriVo) {
        String str = StringUtils.join(distriVo.getStaffIds(), ",");
        Appointment appoint = appointmentMapper
                .selectOne(new QueryWrapper<Appointment>().lambda().eq(Appointment::getId, distriVo.getAppId()));
        if (!appoint.getAppointIsDetection().equals(0)) {
            return CommEntity.create("该预约车辆已经在检测或者检测完成!!!", 500);
        }

        Appointment appointment = new Appointment();
        appointment.setId(distriVo.getAppId());
        //从未检测的状态改为检测状态
        appointment.setAppointIsDetection(1);
        appointment.setAppointStaff(str);
        int i = appointmentMapper.updateById(appointment);
        if (i != 1) {
            return CommEntity.create("未找到对应的预约信息", 500);
        }
        CommEntity commEntity = manageFeign.updateDistributionStaff(distriVo.getStaffIds(), 1);
        if (!commEntity.getCode().equals(200)) {
            throw new RuntimeException("分配员工过程出错");
        }
        return CommEntity.create(i, 200);
    }

    /**
     * 员工得到对应预约信息
     */
    @Override
    public CommEntity getAppointStaff(User user) {
        CommEntity staff = manageFeign.getAppStaff(user.getUsername());
        if (staff.getCode() != 200) {
            return CommEntity.create("当前员工牌号不存在", 500);
        }
        String staffId = staff.getData().toString();
        //得到当前正在进行检测的预约信息
        List<Integer> appointIds = new ArrayList<>();
        List<Appointment> applist = appointmentMapper.selectList(new QueryWrapper<Appointment>()
                .lambda().eq(Appointment::getAppointIsDetection, 1));
        applist.forEach(res -> {
            if (StringUtils.contains(res.getAppointStaff(), staffId)) {
                appointIds.add(res.getId());
            }
        });
        if (CollectionUtils.isEmpty(appointIds)) {
            //表示当前员工没有任务可言
            return CommEntity.create(10086, 200);
        }
        List<Appointment> appointments = appointmentMapper.appointStaff(appointIds);
        return CommEntity.create(appointments, 200);
    }
}
