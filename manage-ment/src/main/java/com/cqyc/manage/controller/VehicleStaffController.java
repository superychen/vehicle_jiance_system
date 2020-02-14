package com.cqyc.manage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.VehicleStaff;
import com.cqyc.manage.service.IVehicleStaffService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 员工信息表 前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2020-02-05
 */
@RestController
@RequestMapping("/manage")
public class VehicleStaffController {

    @Autowired
    private IVehicleStaffService vehicleStaffService;

    /**
     * 新增员工
     */
    @PostMapping("staff")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity addStaff(@RequestBody @Validated VehicleStaff staff, BindingResult result) {
        if (result.hasFieldErrors()) {
            return CommEntity.create(result.getAllErrors().get(0).getDefaultMessage(), 500);
        }
        return vehicleStaffService.addStaff(staff);
    }


    /**
     * 查询所有员工
     */
    @GetMapping("staff")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity getStaffs(@RequestParam(value = "chooseSelect", required = false) Integer chooseSelect,
                                @RequestParam(value = "selectName", required = false) String selectName,
                                @RequestParam("pageNo") Integer pageNo,
                                @RequestParam("pageSize") Integer pageSize) {
        if (chooseSelect != null) {
            if (chooseSelect != 1 && chooseSelect != 2) {
                return CommEntity.create("老铁,莫想了", 500);
            }
        }
        return vehicleStaffService.getStaffs(chooseSelect, selectName, pageNo, pageSize);
    }


    @PutMapping("staff")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity updateStaffs(@RequestBody @Validated VehicleStaff staff, BindingResult result) {
        if (result.hasFieldErrors()) {
            return CommEntity.create(result.getAllErrors().get(0).getDefaultMessage(), 500);
        }
        return vehicleStaffService.updateStaffs(staff);
    }

    /**
     * 分配员工
     */
    @GetMapping("distri/staff")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity getDistributionStaff(@RequestParam("pageNo") Integer pageNo,
                                           @RequestParam("pageSize") Integer pageSize) {
        if (pageNo < 1) {
            return CommEntity.create("无效的页数请求参数", 500);
        }
        return vehicleStaffService.getDistributionStaff(pageNo, pageSize);
    }


    /**
     * TODO 需要分布式事务
     */
    @PutMapping("distri/staff")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOY')")
    public CommEntity updateDistributionStaff(@RequestParam("staffIds") List<String> staffIds,
                                              @RequestParam("restTimeStatus") Integer restTimeStatus) {
        if (staffIds.size() == 0 || staffIds.size() > 3) {
            return CommEntity.create("分配的员工不能为空且不能超过3名", 500);
        }
        staffIds.forEach(res -> {
            VehicleStaff vehicleStaff = new VehicleStaff();
            vehicleStaff.setId(res);
            if (restTimeStatus == 1) {
                vehicleStaff.setStaffRestTime(restTimeStatus);
            } else {
                vehicleStaff.setStaffRestTime(0);
            }
            boolean b = vehicleStaffService.updateById(vehicleStaff);
            if (!b) {
                throw new RuntimeException("修改过程出错");
            }
        });
        return CommEntity.create("ok", 200);
    }

    /**
     * 得到预约员工的id
     *
     * @param staffNumber 员工牌
     */
    @GetMapping("app/staff")
    public CommEntity getAppStaff(@RequestParam("staffNumber") String staffNumber) {
        VehicleStaff staff = vehicleStaffService.getOne(new QueryWrapper<VehicleStaff>()
                .lambda().eq(VehicleStaff::getStaffNumber, staffNumber));
        if (staff == null) {
            return CommEntity.create("当前员工牌号不存在", 500);
        }
        return CommEntity.create(staff.getId(), 200);
    }
}
