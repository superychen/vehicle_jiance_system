package com.cqyc.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.Permission;
import com.cqyc.manage.domain.User;
import com.cqyc.manage.mapper.PermissionMapper;
import com.cqyc.manage.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2019-11-05
 */
@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public CommEntity selectPermission(Integer rid) {
        List<Permission> permissions = permissionMapper.selectPermission(rid);
        if (CollectionUtils.isEmpty(permissions)) {
            return CommEntity.create("未查找到对应的权限", 500);
        }
        return CommEntity.create(permissions, 200);
    }

    /**
     * 分页查询所有分页权限
     *
     * @param pageNo   　当前页
     * @param pageSize 　每一页的个数
     * @return
     */
    @Override
    public CommEntity pageAllPermission(Integer pageNo, Integer pageSize) {
        IPage<Permission> iPage = permissionMapper.selectPage(new Page<>(pageNo, pageSize), null);
        if (iPage.getTotal() <= 0) {
            return CommEntity.create("未查询到数据", 500);
        }
        return CommEntity.create(iPage, 200);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommEntity addPermission(Permission permission) {
        int insertRes = permissionMapper.insert(permission);
        if (insertRes != 1) {
            return CommEntity.create("新增权限失败", 500);
        }
        return CommEntity.create(insertRes, 200);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommEntity deletePermissionOne(Integer pid) {
        //查询权限角色表是否有对应的数据,如果有,无法删除
        int perRes = permissionMapper.findPerRole(pid);
        log.info("查询出来的集合　---> {}", perRes);
        if (perRes > 0) {
            return CommEntity.create("该权限已经有角色拥有,无法删除", 500);
        }
        int i = permissionMapper.deleteById(pid);
        if (i != 1) {
            return CommEntity.create("删除失败", 500);
        }
        return CommEntity.create(i, 200);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommEntity deletePermissionMore(Integer[] pids) {
        int perRes = permissionMapper.findPerRoleForPid(Arrays.asList(pids));
        log.info("查询出来的集合　---> {}", perRes);
        if (perRes > 0) {
            return CommEntity.create("删除的权限中有角色已经在使用,无法删除", 500);
        }
        //批量删除
        int i = permissionMapper.deleteBatchIds(Arrays.asList(pids));
        if (i <= 1) {
            return CommEntity.create("删除失败", 500);
        }
        return CommEntity.create(i, 200);
    }

}
