package com.cqyc.vehicle.controller;


import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.service.IStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 汽车检测环节表 前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/vehicle")
public class StepController {

    @Autowired
    private IStepService stepService;

    @GetMapping("step")
    @PreAuthorize("hasRole('ROLE_EMPLOY')")
    public CommEntity vehicleStep() {
        return stepService.vehicleStep();
    }

}
