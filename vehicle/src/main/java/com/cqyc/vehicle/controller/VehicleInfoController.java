package com.cqyc.vehicle.controller;


import com.cqyc.vehicle.domain.Appointment;
import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.domain.vo.BankVo;
import com.cqyc.vehicle.domain.vo.VehicleInfoVo;
import com.cqyc.vehicle.service.IVehicleInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 车辆信息表 前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2020-01-27
 */
@RestController
@RequestMapping("vehicle")
@Slf4j
public class VehicleInfoController {
    @Autowired
    private IVehicleInfoService vehicleInfoService;

    @PostMapping("bank")
    public CommEntity verityBank(@RequestBody @Validated BankVo bankVo, BindingResult result) {
        if (result.hasFieldErrors()) {
            return CommEntity.create(result.getAllErrors().get(0).getDefaultMessage(), 500);
        }
        return vehicleInfoService.verityBank(bankVo);
    }

    @PostMapping("info")
    public CommEntity vehicleInfo(@RequestBody @Validated VehicleInfoVo vehicleInfoVo, BindingResult result,
                                  @RequestHeader("Authorization") String token) {
        if (result.hasFieldErrors()) {
            return CommEntity.create(result.getAllErrors().get(0).getDefaultMessage(), 500);
        }
        if (!StringUtils.equals(vehicleInfoVo.getVehicleUser(), vehicleInfoVo.getBankVo().getBankName())) {
            return CommEntity.create("车主姓名与银行卡姓名不一致,请填写正确的车主姓名", 500);
        }
        //填写身份证号
        vehicleInfoVo.setVehicleUserIdNumber(vehicleInfoVo.getBankVo().getBankCardNum());
        vehicleInfoVo.setVehicleUserTelephone(vehicleInfoVo.getBankVo().getPhoneNum());
        return vehicleInfoService.vehicleInfo(vehicleInfoVo, token);
    }

    /**
     * 当前用户是否输入过车辆信息
     *
     * @return comm
     */
    @GetMapping("info")
    public CommEntity isVehicle(@RequestHeader("Authorization") String token) {
        return vehicleInfoService.isVehicle(token);
    }

    @PostMapping("appoint")
    public CommEntity appointVehicle(@RequestParam("vehicleInfoId") Integer vehicleInfoId,
                                     @RequestParam("appointAddress") String appointAddress,
                                     @RequestParam("appointUsername") String appointUsername) {
        if (vehicleInfoId == 0) {
            return CommEntity.create("煞笔,别走后台,给劳资爬!!!", 200);
        }
        Appointment appointment = new Appointment();
        appointment.setVehicleInfoId(vehicleInfoId);
        appointment.setAppointAddress(appointAddress);
        appointment.setAppointUsername(appointUsername);
        return vehicleInfoService.appointVehicle(appointment);
    }


}
