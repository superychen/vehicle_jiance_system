package com.cqyc.manage.service;

import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.Permission;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cqyc
 * @since 2019-11-05
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 根据角色id查询对应的权限
     * @param rid 角色id
     * @return 权限
     */
    CommEntity selectPermission(Integer rid);

    /**
     * 分页查询权限所有
     * @param pageNo　当前页
     * @param pageSize　每一页的个数
     * @return
     */
    CommEntity pageAllPermission(Integer pageNo, Integer pageSize);

    /**
     * 新增对应权限
     * @param permission　权限实体类
     * @return　ｃｏｍｍ
     */
    CommEntity addPermission(Permission permission);

    /**
     * 删除一个权限
     * @param pid　权限ｉｄ
     * @return comm
     */
    CommEntity deletePermissionOne(Integer pid);

    /**
     *　批量删除权限
     * @param pids 权限ids
     * @return comm
     */
    CommEntity deletePermissionMore(Integer[] pids);

}
