package com.cqyc.manage.mapper;

import com.cqyc.manage.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author cqyc
 * @since 2019-11-05
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 通过角色id查询对应的权限
     * @param rid 角色id
     * @return permission
     */
    List<Permission> selectPermission(@Param("rid") Integer rid);

    /**
     * 插入角色权限表中数据
     * @param pid 权限id
     * @param rid 角色id
     * @return 是否插入成功
     */
    int addRolePermission(@Param("pid") Integer pid,@Param("rid") Integer rid);

    /**
     * 删除角色权限表中的对应的角色ｉｄ
     * @param rids 角色ids
     * @return comm
     */
    int deleteRoleIds(@Param("rids") List<Integer> rids);

    /**
     * 查询角色权限表中是否有数据
     * @param pid 权限id
     * @return int
     */
    int findPerRole(@Param("pid") Integer pid);

    /**
     * 循环查询中间表中的ids
     * @param pids pids
     * @return comm
     */
    int findPerRoleForPid(@Param("pids") List<Integer> pids);
}
