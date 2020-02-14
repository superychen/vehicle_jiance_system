package com.cqyc.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqyc.auth.domain.User;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cqyc
 * @since 2019-10-26
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户对应的角色和权限
     * @param username
     * @return
     */
    User selectUserRoleAndPer(@Param("username") String username);
}
