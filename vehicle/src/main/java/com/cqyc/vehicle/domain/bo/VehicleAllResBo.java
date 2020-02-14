package com.cqyc.vehicle.domain.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.List;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-2-14
 */
@ExcelTarget("VehicleAllResBo")
@Data
public class VehicleAllResBo {

    @Excel(name = "车牌号", width = 12, needMerge = true)
    private String vehicleNumber;

    @Excel(name = "预约地址", width = 25, needMerge = true)
    private String appointAddress;

    @ExcelCollection(name = "检查结果")
    private List<VehicleResultBo> vehicleResultBos;
}
