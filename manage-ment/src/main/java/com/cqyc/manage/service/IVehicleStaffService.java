package com.cqyc.manage.service;

import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.VehicleStaff;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 员工信息表 服务类
 * </p>
 *
 * @author cqyc
 * @since 2020-02-05
 */
public interface IVehicleStaffService extends IService<VehicleStaff> {

    /**
     * 添加员工
     *
     * @param staff 员工类
     * @return
     */
    CommEntity addStaff(VehicleStaff staff);

    /**
     * 得到所有的员工列表
     *
     * @param chooseSelect 是否条件查询: 0:不查询, 1: 根据身份证查询, 2:根据工牌号查询
     * @param selectName   查询的条件
     * @param pageNo       当前页
     * @param pageSize     查询多少页
     * @return comm
     */
    CommEntity getStaffs(Integer chooseSelect, String selectName, Integer pageNo, Integer pageSize);

    /**
     * 修改员工
     *
     * @param staff 员工
     * @return
     */
    CommEntity updateStaffs(VehicleStaff staff);

    /**
     * 分配员工
     *
     * @param pageNo   页数
     * @param pageSize 显示的个数
     * @return
     */
    CommEntity getDistributionStaff(Integer pageNo, Integer pageSize);
}
