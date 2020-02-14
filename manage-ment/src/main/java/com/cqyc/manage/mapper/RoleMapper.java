package com.cqyc.manage.mapper;

import com.cqyc.manage.domain.Permission;
import com.cqyc.manage.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author cqyc
 * @since 2019-10-15
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色id删除角色权限关联表中信息
     * @param rid 角色id
     */
    void deleteRolePermission(@Param("rid") Integer rid);
}
