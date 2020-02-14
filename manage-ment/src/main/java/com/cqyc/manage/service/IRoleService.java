package com.cqyc.manage.service;

import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cqyc.manage.domain.vo.RoleVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cqyc
 * @since 2019-10-15
 */
public interface IRoleService extends IService<Role> {

    /**
     * 查询所有的角色管理
     * @return 角色
     */
    CommEntity allRole();

    /**
     * 查询用户角色管理
     * @param rname 角色名称
     * @param pageNo 当前页
     * @param pageSize 当前的页数
     * @return 角色信息
     */
    CommEntity allRoleManage(String rname,Integer pageNo, Integer pageSize);

    /**
     * 新增角色
     * @param pid 角色id
     * @param rname 角色名字
     * @return
     */
    CommEntity addRole(Integer[] pid, String rname);

    /**
     * 修改角色
     * @param roleVo 角色vo包
     * @return comm
     */
    CommEntity updateRole(RoleVo roleVo);

    /**
     * 删除角色
     * @param rids　角色ｉｄｓ
     * @return comm
     */
    CommEntity deleteRole(Integer[] rids);
}
