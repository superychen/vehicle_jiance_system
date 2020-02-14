package com.cqyc.vehicle.feign.impl;

import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.feign.ManageFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-2-10
 */
@Component
@Slf4j
public class ManageFeignImpl implements ManageFeign {
    @Override
    public CommEntity updateDistributionStaff(List<String> staffIds, Integer restTimeStatus) {
        log.info("------进入了熔断-------");
        return null;
    }

    @Override
    public CommEntity getAppStaff(String staffNumber) {
        log.info("------进入了熔断-------");
        return null;
    }
}
