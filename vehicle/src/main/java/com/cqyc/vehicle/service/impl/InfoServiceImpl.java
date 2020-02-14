package com.cqyc.vehicle.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqyc.vehicle.domain.Appointment;
import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.domain.User;
import com.cqyc.vehicle.domain.VehicleInfo;
import com.cqyc.vehicle.domain.vo.BankVo;
import com.cqyc.vehicle.domain.vo.VehicleInfoVo;
import com.cqyc.vehicle.feign.LoginFeign;
import com.cqyc.vehicle.mapper.AppointmentMapper;
import com.cqyc.vehicle.mapper.VehicleInfoMapper;
import com.cqyc.vehicle.service.IVehicleInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 车辆信息表 服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2020-01-27
 */
@Service
@Slf4j
public class InfoServiceImpl extends ServiceImpl<VehicleInfoMapper, VehicleInfo> implements IVehicleInfoService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private LoginFeign loginFeign;

    @Autowired
    private VehicleInfoMapper vehicleInfoMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public CommEntity verityBank(BankVo bankVo) {
        String code = redisTemplate.opsForValue().get(bankVo.getPhoneNum() + ":phoneCode");
        if (!StringUtils.equals(bankVo.getPhoneCode(), code)) {
            return CommEntity.create("输入的验证码不正确", 500);
        }
        //todo 进行银行卡4要素认证接口
        return CommEntity.create(bankVo, 200);
    }

    @Override
    public CommEntity vehicleInfo(VehicleInfoVo vehicleInfoVo, String token) {
        VehicleInfo vehicleInfo = vehicleInfoVo.selectOne(new QueryWrapper<VehicleInfo>().lambda().eq(VehicleInfo::getVehicleNumber, vehicleInfoVo.getVehicleNumber()));
        if (vehicleInfo != null) {
            return CommEntity.create("您的车牌号已经被录入,可直接预约", 1024);
        }
        User user = userinfo(token);
        vehicleInfoVo.setVehicleUserId(user.getUid());
        int insert;
        try {
            insert = vehicleInfoMapper.insert(vehicleInfoVo);
            if (insert != 1) {
                return CommEntity.create("数据库出现异常,请稍后重试", 500);
            }
        } catch (Exception e) {
            log.error("数据库抛出异常---->{}", e.getMessage());
            return CommEntity.create("数据库出现异常,请稍后重试", 500);
        }
        return CommEntity.create("汽车信息录入成功", 200);
    }

    @Override
    public CommEntity isVehicle(String token) {
        User user = userinfo(token);
        List<VehicleInfo> vehicleInfos = vehicleInfoMapper.selectList(new QueryWrapper<VehicleInfo>()
                .lambda().eq(VehicleInfo::getVehicleUserId, user.getUid()));
        if (CollectionUtils.isEmpty(vehicleInfos)) {
            return CommEntity.create("当前用户未曾录入过用户汽车信息", 10086);
        }
        VehicleInfo vehicleInfo = new VehicleInfo();
        vehicleInfo.setId(0);
        vehicleInfo.setVehicleNumber("重新录入车辆信息");
        vehicleInfos.add(vehicleInfo);
        return CommEntity.create(vehicleInfos, 200);
    }

    @Override
    public CommEntity appointVehicle(Appointment appointment) {
        appointment.setAppointTime(LocalDateTime.now());
        int insert = appointmentMapper.insert(appointment);
        if (insert != 1) {
            return CommEntity.create("数据库异常", 500);
        }
        return CommEntity.create(insert, 200);
    }

    /**
     * 用户信息
     *
     * @param token
     * @return
     */
    public User userinfo(String token) {
        CommEntity commEntity = loginFeign.userInfo(token);
        //强转为用户,注意出错
        if (commEntity.getCode() != 200) {
            throw new RuntimeException("服务器出现异常");
        }
        String jsonStr = JSONObject.toJSONString(commEntity.getData());
        log.info("转换的用户信息---->{}", jsonStr);
        User user = JSONObject.parseObject(jsonStr, User.class);
        return user;
    }

}
