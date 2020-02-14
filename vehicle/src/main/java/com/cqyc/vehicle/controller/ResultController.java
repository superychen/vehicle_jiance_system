package com.cqyc.vehicle.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqyc.vehicle.comm.EasyPoiUtil;
import com.cqyc.vehicle.comm.HttpClientUtil;
import com.cqyc.vehicle.comm.VerityUtil;
import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.domain.Result;
import com.cqyc.vehicle.domain.User;
import com.cqyc.vehicle.domain.bo.VehicleAllResBo;
import com.cqyc.vehicle.domain.bo.VehicleResultBo;
import com.cqyc.vehicle.domain.vo.VehicleStepVo;
import com.cqyc.vehicle.service.IResultService;
import com.cqyc.vehicle.service.impl.InfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 汽车最终检测结果表 前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2020-02-12
 */
@RestController
@RequestMapping("/vehicle")
@Slf4j
public class ResultController {

    @Autowired
    private IResultService resultService;

    @Autowired
    private InfoServiceImpl infoService;

    @Value("${easy.excel.picurl}")
    private String easyExcelPicUrl;

    /**
     * 员工填写检测结果
     */
    @PostMapping("result")
    @PreAuthorize("hasRole('ROLE_EMPLOY')")
    public CommEntity addVehicleResult(@RequestBody List<VehicleStepVo> stepVo,
                                       @RequestParam("aid") Integer aid) {
        if (CollectionUtils.isEmpty(stepVo)) {
            return CommEntity.create("结果为空!!!", 500);
        }
        for (VehicleStepVo res : stepVo) {
            if (VerityUtil.verityStep(res).getCode() != 200) {
                return VerityUtil.verityStep(res);
            }
        }
        String stepStr = JSON.toJSONString(stepVo);
        Result result = new Result();
        result.setResultContent(stepStr);
        result.setResultAppointId(aid);
        return resultService.addVehicleResult(result);
    }

    /**
     * 用户查询结果
     */
    @GetMapping("result")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public CommEntity getResults(@RequestHeader("Authorization") String token,
                                 @RequestParam("pageNo") Integer pageNo,
                                 @RequestParam("pageSize") Integer pageSize) {
        User user = infoService.userinfo(token);
        return resultService.getResults(user.getUid(), pageNo, pageSize);
    }

    /**
     * 导出单个报表
     */
    @PostMapping("res/report")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public void exportOneReport(@RequestBody List<VehicleResultBo> vehicleResultBos,
                                @RequestParam("vehicleNumber") String vehicleNumber,
                                HttpServletResponse response) {
        vehicleResultBos.forEach(res -> {
            if (res.getConformity() == 1) {
                res.setConformityText("合格");
                res.setIsConformityPercent(res.getIsConformityText());
            } else {
                res.setConformityText("不合格");
                res.setNotConformtyPic(easyExcelPicUrl + res.getNotConformtyPic());
                //读取fastdfs的图片
                res.setBrokePic(HttpClientUtil.getImageFromNetByUrl(res.getNotConformtyPic()));
            }
        });
        EasyPoiUtil.exportExcel(vehicleResultBos, vehicleNumber, "检测结果", VehicleResultBo.class, "cqyc" + ".xls", response);
    }

    @GetMapping("report")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public void exportAllReport(@RequestHeader("Authorization") String token,
                                HttpServletResponse response) {
        User user = infoService.userinfo(token);
        List<VehicleAllResBo> vehicleAllResBos = resultService.exportAllReport(user);
        vehicleAllResBos.forEach(resBos -> {
            resBos.getVehicleResultBos().forEach(res -> {
                if (res.getConformity() == 1) {
                    res.setConformityText("合格");
                    res.setIsConformityPercent(res.getIsConformityText());
                } else {
                    res.setConformityText("不合格");
                    res.setNotConformtyPic(easyExcelPicUrl + res.getNotConformtyPic());
                    //读取fastdfs的图片
                    res.setBrokePic(HttpClientUtil.getImageFromNetByUrl(res.getNotConformtyPic()));
                }
            });
        });
        EasyPoiUtil.exportExcel(vehicleAllResBos, user.getUsername(), "检测结果", VehicleAllResBo.class, user.getUsername() + ".xls", response);
    }

}
