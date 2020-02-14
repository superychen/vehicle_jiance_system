package com.cqyc.vehicle.controller;


import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.domain.User;
import com.cqyc.vehicle.domain.vo.DistriVo;
import com.cqyc.vehicle.feign.LoginFeign;
import com.cqyc.vehicle.service.IAppointmentService;
import com.cqyc.vehicle.service.impl.InfoServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 车辆预约表 前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2020-01-31
 */
@RestController
@RequestMapping("/appoint")
public class AppointmentController {

    @Autowired
    private InfoServiceImpl infoService;

    @Autowired
    private IAppointmentService appointmentService;

    @GetMapping("res")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public CommEntity appointStatus(@RequestHeader("Authorization") String token) {
        User user = infoService.userinfo(token);
        return appointmentService.appointStatus(user.getUid());
    }

    @GetMapping("app")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity getAppoints(@RequestParam("pageNo") Integer pageNo,
                                  @RequestParam("pageSize") Integer pageSize) {
        return appointmentService.getAppoints(pageNo, pageSize);
    }

    /**
     * 分配员工
     */
    @PostMapping("distri")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity addDistributionStaff(@RequestBody DistriVo distriVo) {
        if (distriVo.getAppId() <= 0) {
            return CommEntity.create("不合法的预约id", 500);
        }
        if (distriVo.getStaffIds().size() == 0 || distriVo.getStaffIds().size() > 3) {
            return CommEntity.create("分配的员工不能为空且不能超过3名", 500);
        }
        return appointmentService.addDistributionStaff(distriVo);
    }

    /**
     * 查询员工对应的预约信息
     */
    @GetMapping("staff")
    @PreAuthorize("hasRole('ROLE_EMPLOY')")
    public CommEntity getAppointStaff(@RequestHeader("Authorization") String token) {
        User user = infoService.userinfo(token);
        return appointmentService.getAppointStaff(user);
    }

}
