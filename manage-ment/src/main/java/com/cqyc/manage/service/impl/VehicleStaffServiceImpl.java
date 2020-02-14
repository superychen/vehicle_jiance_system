package com.cqyc.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqyc.manage.comm.util.OtherUtil;
import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.User;
import com.cqyc.manage.domain.VehicleStaff;
import com.cqyc.manage.mapper.UserMapper;
import com.cqyc.manage.mapper.VehicleStaffMapper;
import com.cqyc.manage.service.IVehicleStaffService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 * 员工信息表 服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2020-02-05
 */
@Service
public class VehicleStaffServiceImpl extends ServiceImpl<VehicleStaffMapper, VehicleStaff> implements IVehicleStaffService {

    @Autowired
    private VehicleStaffMapper vehicleStaffMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加员工
     */
    @Override
    public CommEntity addStaff(VehicleStaff staff) {
        //生成6位员工工牌号
        String sixRandom = OtherUtil.sixRandom();
        VehicleStaff vehicleStaff = vehicleStaffMapper.selectOne(new QueryWrapper<VehicleStaff>()
                .lambda().eq(VehicleStaff::getStaffNumber, sixRandom));
        if (vehicleStaff != null) {
            sixRandom = OtherUtil.sixRandom();
        }
        User user = new User();
        //给员工创建一个账号,初始密码6个1
        user.setUsername(sixRandom);
        user.setPassword(new BCryptPasswordEncoder().encode("111111"));
        user.setUserImg("group1/M00/00/00/wKgrDV4vuROAbl8dAAMAUIt99c8253.jpg");
        //生成一个初始用户
        userMapper.insert(user);
        User user1 = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, sixRandom));
        //插入角色,3表示角色为员工,写死
        userMapper.insertUserRole(user1.getUid(), Arrays.asList(3));
        String uuid = OtherUtil.getUuid();
        staff.setStaffNumber(sixRandom);
        staff.setId(uuid);
        staff.setStaffUserId(user1.getUid());
        //生成员工表
        int insert = vehicleStaffMapper.insert(staff);
        return CommEntity.create(insert, 200);
    }

    @Override
    public CommEntity getStaffs(Integer chooseSelect, String selectName, Integer pageNo, Integer pageSize) {
        LambdaQueryWrapper<VehicleStaff> wrapper = null;
        Page<VehicleStaff> staffPage = new Page<>(pageNo, pageSize);
        if (chooseSelect != null) {
            if (chooseSelect.equals(1) && StringUtils.isNotBlank(selectName)) {
                wrapper = new QueryWrapper<VehicleStaff>().lambda().like(VehicleStaff::getStaffIdCard, selectName);
            } else if (chooseSelect.equals(2) && StringUtils.isNotBlank(selectName)) {
                wrapper = new QueryWrapper<VehicleStaff>().lambda().like(VehicleStaff::getStaffNumber, selectName);
            }
        }
        IPage<VehicleStaff> pageRes = vehicleStaffMapper.selectPage(staffPage, wrapper);
        if (CollectionUtils.isEmpty(pageRes.getRecords())) {
            return CommEntity.create("没有查询到您想要的结果", 203);
        }
        return CommEntity.create(pageRes, 200);
    }

    /**
     * 修改员工
     *
     * @param staff 员工
     * @return
     */
    @Override
    public CommEntity updateStaffs(VehicleStaff staff) {
        VehicleStaff vehicleStaff = vehicleStaffMapper.selectOne(new QueryWrapper<VehicleStaff>()
                .lambda().eq(VehicleStaff::getStaffNumber, staff.getStaffNumber()).eq(VehicleStaff::getId, staff.getId()));
        if (vehicleStaff == null) {
            return CommEntity.create("无法修改,请不要访问后台!!!", 500);
        }
        int i = vehicleStaffMapper.updateById(staff);
        if (i != 1) {
            return CommEntity.create("数据库修改失败", 500);
        }
        return CommEntity.create(i, 200);
    }

    /**
     * 分配员工
     *
     * @param pageNo 页数
     * @return
     */
    @Override
    public CommEntity getDistributionStaff(Integer pageNo, Integer pageSize) {
        Page<VehicleStaff> staffPage = new Page<>(pageNo, pageSize);
        //查询当前有空闲时间的员工
        IPage<VehicleStaff> vehicleStaffPage = vehicleStaffMapper.selectPage(staffPage,
                new QueryWrapper<VehicleStaff>().lambda().eq(VehicleStaff::getStaffRestTime, 0));
        if (CollectionUtils.isEmpty(vehicleStaffPage.getRecords())) {
            return CommEntity.create("查询数据为空", 203);
        }
        return CommEntity.create(vehicleStaffPage, 200);
    }
}
