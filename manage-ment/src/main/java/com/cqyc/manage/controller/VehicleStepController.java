package com.cqyc.manage.controller;


import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.VehicleStep;
import com.cqyc.manage.service.IVehicleStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 汽车检测环节表 前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2020-02-07
 */
@RestController
@RequestMapping("/manage")
public class VehicleStepController {

    @Autowired
    private IVehicleStepService vehicleStepService;

    @PostMapping("step")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity addStep(@RequestBody @Validated VehicleStep vehicleStep, BindingResult result) {
        if (result.hasFieldErrors()) {
            return CommEntity.create(result.getAllErrors().get(0).getDefaultMessage(), 500);
        }
        return vehicleStepService.addStep(vehicleStep);
    }


    @GetMapping("step")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity getSteps(@RequestParam("pageNo") Integer pageNo,
                               @RequestParam("pageSize") Integer pageSize) {
        return vehicleStepService.getSteps(pageNo, pageSize);
    }

    @PutMapping("step")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity updateStep(@RequestBody @Validated VehicleStep vehicleStep, BindingResult result) {
        if (result.hasFieldErrors()) {
            return CommEntity.create(result.getAllErrors().get(0).getDefaultMessage(), 500);
        }
        return vehicleStepService.updateStep(vehicleStep);
    }

    @DeleteMapping("step")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity deleteStaff(@RequestParam("id") Integer id) {
        boolean b = vehicleStepService.removeById(id);
        if (!b) {
            return CommEntity.create("删除失败", 500);
        }
        return CommEntity.create(b, 200);
    }

}
