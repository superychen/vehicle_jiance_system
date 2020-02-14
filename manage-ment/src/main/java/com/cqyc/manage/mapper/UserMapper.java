package com.cqyc.manage.mapper;

import com.cqyc.manage.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author cqyc
 * @since 2019-10-14
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 删除用户角色中间表中角色的信息
     *
     * @param rids 　角色id
     * @return comm
     */
    int deleteRole(@Param("rids") List<Integer> rids);

    /**
     * 得到一个用户,这个用户包含角色
     *
     * @param uid
     * @return
     */
    User getOneUserRole(@Param("uid") Integer uid);

    /**
     * 插入用户角色
     *
     * @param uid
     * @param roleIds
     * @return
     */
    int insertUserRole(@Param("uid") Integer uid, @Param("roleIds") List<Integer> roleIds);

    /**
     * 删除对应用户的角色,先删除后添加
     * @param uid
     * @return
     */
    int deleteUserRole(Integer uid);
}
