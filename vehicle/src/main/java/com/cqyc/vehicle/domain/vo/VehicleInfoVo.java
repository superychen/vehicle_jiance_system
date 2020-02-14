package com.cqyc.vehicle.domain.vo;

import com.cqyc.vehicle.domain.VehicleInfo;
import lombok.Data;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-29
 */
@Data
public class VehicleInfoVo extends VehicleInfo {
    private BankVo bankVo;
}
