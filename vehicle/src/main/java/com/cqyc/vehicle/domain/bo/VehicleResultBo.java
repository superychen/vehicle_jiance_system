package com.cqyc.vehicle.domain.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: cqyc
 * Description:　讲结果导入为excel
 * Created by cqyc on 20-2-13
 */
@Data
@ExcelTarget("VehicleResultBo")
public class VehicleResultBo implements Serializable {

    @Excel(name = "编号", orderNum = "1")
    private Integer id;

    @Excel(name = "检查名称", width = 15, orderNum = "2")
    private String stepTitle;

    /**
     * 接收是否合格,然后在设值
     */
    private Integer conformity;

    @Excel(name = "是否合格", orderNum = "3")
    private String conformityText;

    private Integer isConformityText;

    @Excel(name = "所达合格率", orderNum = "4", suffix = "%")
    private Integer isConformityPercent;

    @Excel(name = "损坏详情", orderNum = "5")
    private String notConformtyText;

    private String notConformtyPic;

    @Excel(name = "损坏图片", type = 2, width = 50, height = 60, orderNum = "6", imageType = 2)
    private byte[] brokePic;

}
