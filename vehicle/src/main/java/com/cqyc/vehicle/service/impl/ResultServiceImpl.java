package com.cqyc.vehicle.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqyc.vehicle.domain.Appointment;
import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.domain.Result;
import com.cqyc.vehicle.domain.User;
import com.cqyc.vehicle.domain.bo.VehicleAllResBo;
import com.cqyc.vehicle.domain.bo.VehicleResultBo;
import com.cqyc.vehicle.feign.ManageFeign;
import com.cqyc.vehicle.mapper.AppointmentMapper;
import com.cqyc.vehicle.mapper.ResultMapper;
import com.cqyc.vehicle.service.IResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 汽车最终检测结果表 服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2020-02-12
 */
@Service
public class ResultServiceImpl extends ServiceImpl<ResultMapper, Result> implements IResultService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private ResultMapper resultMapper;

    @Autowired
    private ManageFeign manageFeign;

    /**
     * 这里必须加锁
     *
     * @param result 实体类
     * @return
     */
    @Override
    public synchronized CommEntity addVehicleResult(Result result) {
        List<Appointment> list = appointmentMapper.appointStaff(Arrays.asList(result.getResultAppointId()));
        if (CollectionUtils.isEmpty(list) || CollectionUtils.size(list) > 1) {
            return CommEntity.create("预约信息出现后问题", 500);
        }
        Appointment appointment = list.get(0);
        //得到预约表中对应的员工id
        String[] staffs = StringUtils.split(appointment.getAppointStaff(), ",");
        if (appointment.getAppointIsDetection() != 1) {
            return CommEntity.create("该预约信息已经提交结果了哟!!", 500);
        }
        if (staffs == null || staffs.length == 0) {
            return CommEntity.create("未找到该预约信息!!", 500);
        }
        //修改对应检测员工为空闲状态
        List<String> stafList = new ArrayList<>(Arrays.asList(staffs));
        CommEntity commEntity = manageFeign.updateDistributionStaff(stafList, 2);
        if (!commEntity.getCode().equals(200)) {
            throw new RuntimeException("员工修改失败,服务器出现异常!!");
        }

        //改变当前预约订单的状态为检测完毕
        appointment.setAppointIsDetection(2);
        int i = appointmentMapper.updateById(appointment);
        if (i != 1) {
            throw new RuntimeException("系统出现未知异常,请稍后再次尝试!!");
        }

        //插入结果表
        result.setResultUserId(appointment.getVehicleInfo().getVehicleUserId());
        result.setResultTime(LocalDateTime.now());
        int insert = resultMapper.insert(result);
        if (insert != 1) {
            throw new RuntimeException("系统出现未知异常,请稍后再次尝试!!");
        }

        return CommEntity.create("上传结果成功", 200);
    }


    @Override
    public CommEntity getResults(Integer uid, Integer pageNo, Integer pageSize) {
        LocalDateTime localDateTime = LocalDateTime.now().minusYears(2);
        //查询两年前到现在中间用户的预约结果
        Page<Result> resultPage = new Page<>(pageNo, pageSize);
        IPage<Result> resPage = resultMapper.selectPage(resultPage, new QueryWrapper<Result>()
                .lambda().eq(Result::getResultUserId, uid).ge(Result::getResultTime, localDateTime));

        if (CollectionUtils.isEmpty(resPage.getRecords())) {
            return CommEntity.create("当前没有任何数据哟!!!", 500);
        }
        //用流的形式取出结果的结果中的预约id
///        List<Integer> appIds = results.stream().map(Result::getResultAppointId).collect(Collectors.toList());

        //循环设置结果对应的预约信息
        resPage.getRecords().forEach(res -> res.setAppointment(appointmentMapper.oneAppointInfo(res.getResultAppointId())));
        return CommEntity.create(resPage, 200);
    }


    @Override
    public List<VehicleAllResBo> exportAllReport(User user) {
        LocalDateTime localDateTime = LocalDateTime.now().minusYears(2);
        //查询两年前到现在中间用户的预约结果
        List<Result> results = resultMapper.selectList(new QueryWrapper<Result>().lambda().eq(Result::getResultUserId, user.getUid())
                .ge(Result::getResultTime, localDateTime));
        if (CollectionUtils.isEmpty(results)) {
            throw new RuntimeException("目前还没有检测完成的预约车辆哟！！！");
        }
        List<VehicleAllResBo> vehicleAllResBos = new ArrayList<>();
        results.forEach(res -> {
            res.setAppointment(appointmentMapper.oneAppointInfo(res.getResultAppointId()));
            VehicleAllResBo resBo = new VehicleAllResBo();
            //设置预约地址和车牌号
            resBo.setAppointAddress(res.getAppointment().getAppointAddress());
            resBo.setVehicleNumber(res.getAppointment().getVehicleInfo().getVehicleNumber());
            List<VehicleResultBo> vehicleResultBos = new ArrayList<>();
            JSONArray array = JSONArray.parseArray(res.getResultContent());
            //循环每一个转换出来的数组
            array.forEach(arr -> {
                VehicleResultBo vehicleResultBo = JSONObject.parseObject(JSON.toJSONString(arr), VehicleResultBo.class);
                vehicleResultBos.add(vehicleResultBo);
            });
            resBo.setVehicleResultBos(vehicleResultBos);
            vehicleAllResBos.add(resBo);
        });
        return vehicleAllResBos;
    }
}
