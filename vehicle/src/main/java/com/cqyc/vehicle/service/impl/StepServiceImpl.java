package com.cqyc.vehicle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.domain.Step;
import com.cqyc.vehicle.mapper.StepMapper;
import com.cqyc.vehicle.service.IStepService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 汽车检测环节表 服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2020-02-11
 */
@Service
public class StepServiceImpl extends ServiceImpl<StepMapper, Step> implements IStepService {


    @Autowired
    private StepMapper stepMapper;

    /**
     * 车辆结果检查步骤
     */
    @Override
    public CommEntity vehicleStep() {
        List<Step> steps = stepMapper.selectList(new QueryWrapper<>());
        return CommEntity.create(steps, 200);
    }
}
