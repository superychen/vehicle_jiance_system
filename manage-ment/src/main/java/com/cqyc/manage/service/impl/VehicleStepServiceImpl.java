package com.cqyc.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.VehicleStaff;
import com.cqyc.manage.domain.VehicleStep;
import com.cqyc.manage.mapper.VehicleStepMapper;
import com.cqyc.manage.service.IVehicleStepService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 汽车检测环节表 服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2020-02-07
 */
@Service
public class VehicleStepServiceImpl extends ServiceImpl<VehicleStepMapper, VehicleStep> implements IVehicleStepService {

    @Autowired
    private VehicleStepMapper vehicleStepMapper;

    @Override
    public CommEntity addStep(VehicleStep vehicleStep) {
        int insert = vehicleStepMapper.insert(vehicleStep);
        if (insert != 1) {
            return CommEntity.create("数据库异常", 500);
        }
        return CommEntity.create(insert, 200);
    }


    @Override
    public CommEntity getSteps(Integer pageNo, Integer pageSize) {
        Page<VehicleStep> stepPage = new Page<>(pageNo, pageSize);
        IPage<VehicleStep> vehicleStepIPage = vehicleStepMapper.selectPage(stepPage, new QueryWrapper<>());
        if (CollectionUtils.isEmpty(vehicleStepIPage.getRecords())) {
            return CommEntity.create("无数据", 500);
        }
        return CommEntity.create(vehicleStepIPage, 200);
    }

    @Override
    public CommEntity updateStep(VehicleStep vehicleStep) {
        if (vehicleStep.getId() == null) {
            return CommEntity.create("无法修改用户,请认真的请求!!!", 500);
        }
        int i = vehicleStepMapper.updateById(vehicleStep);
        if (i != 1) {
            return CommEntity.create("数据库异常", 500);
        }
        return CommEntity.create(i, 200);
    }


}
