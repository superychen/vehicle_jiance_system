package com.cqyc.vehicle.feign;

import com.cqyc.vehicle.config.security.FeignConfiguration;
import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.feign.impl.ManageFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-2-10
 */
@FeignClient(value = "management", configuration = FeignConfiguration.class, fallback = ManageFeignImpl.class)
public interface ManageFeign {

    /**
     * 修改员工的信息
     *
     * @param staffIds
     * @param restTimeStatus 员工的状态,是否被分配任务
     * @return
     */
    @PutMapping("manage/distri/staff")
    CommEntity updateDistributionStaff(@RequestParam("staffIds") List<String> staffIds,
                                       @RequestParam("restTimeStatus") Integer restTimeStatus);

    /**
     * 得到员工的id
     *
     * @param staffNumber
     * @return
     */
    @GetMapping("manage/app/staff")
    CommEntity getAppStaff(@RequestParam("staffNumber") String staffNumber);
}
