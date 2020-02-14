package com.cqyc.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.Permission;
import com.cqyc.manage.domain.Role;
import com.cqyc.manage.domain.vo.RoleVo;
import com.cqyc.manage.mapper.MenuMapper;
import com.cqyc.manage.mapper.PermissionMapper;
import com.cqyc.manage.mapper.RoleMapper;
import com.cqyc.manage.mapper.UserMapper;
import com.cqyc.manage.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2019-10-15
 */
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public CommEntity allRole() {
        List<Role> roles = roleMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isEmpty(roles)) {
            return CommEntity.create("查询角色为空", 500);
        }
        return CommEntity.create(roles, 200);
    }

    /**
     * 角色管理中的角色信息
     */
    @Override
    public CommEntity allRoleManage(String rname, Integer pageNo, Integer pageSize) {
        LambdaQueryWrapper<Role> wrapper = null;
        Page<Role> rolePage = new Page<>(pageNo, pageSize);
        if (StringUtils.isNotBlank(rname)) {
            wrapper = new QueryWrapper<Role>().lambda().like(Role::getRname, rname);
        }
        IPage<Role> roleIPage = roleMapper.selectPage(rolePage, wrapper);
        List<Role> records = roleIPage.getRecords();
        records.forEach(role -> {
            List<Permission> permissions = permissionMapper.selectPermission(role.getRid());
            role.setPermissions(permissions);
        });
        return CommEntity.create(roleIPage, 200);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommEntity addRole(Integer[] pid, String rname) {
        Role role = new Role();
        role.setRname(rname);
        int insert = roleMapper.insert(role);
        if (insert != 1) {
            return CommEntity.create("无法插入用户角色信息", 500);
        }

        Role roleOne = roleMapper.selectOne(new QueryWrapper<Role>().lambda().eq(Role::getRname, rname));
        for (int i = 0; i < pid.length; i++) {
            permissionMapper.addRolePermission(pid[i], roleOne.getRid());
        }
        return CommEntity.create(insert, 200);
    }

    /**
     * 修改角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommEntity updateRole(RoleVo roleVo) {
        int i = roleMapper.updateById(roleVo);
        if (i != 1) {
            return CommEntity.create("修改用户角色失败", 500);
        }
        roleMapper.deleteRolePermission(roleVo.getRid());
        for (int i1 = 0; i1 < roleVo.getPid().length; i1++) {
            permissionMapper.addRolePermission(roleVo.getPid()[i1], roleVo.getRid());
        }
        return CommEntity.create(i, 200);
    }

    /**
     * 删除角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommEntity deleteRole(Integer[] rids) {
        List<Integer> rides = Arrays.asList(rids);
        int i = roleMapper.deleteBatchIds(rides);
        int isDel = permissionMapper.deleteRoleIds(rides);
        int mDel = menuMapper.deleteRole(rides);
        int uDel = userMapper.deleteRole(rides);
        if (i == 0) {
            return CommEntity.create("删除角色出现异常", 500);
        }
        //循环删除菜单角色对应的信息
        return CommEntity.create(i, 200);
    }
}
