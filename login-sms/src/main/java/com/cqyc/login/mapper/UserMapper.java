package com.cqyc.login.mapper;

import com.cqyc.login.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author cqyc
 * @since 2019-10-26
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 插入用户角色，从前台注册默认接口为customer
     *
     * @param uid
     * @param rid
     * @return
     */
    int insertUserRole(@Param("uid") Integer uid, @Param("rid") Integer rid);

    /**
     * 统计五个月前到现在的注册用户数量
     *
     * @param localMonth  当前月份
     * @param beforeMonth 　当前的5个月
     * @return
     */
    List<Map> countRegister(@Param("localMonth") String localMonth, @Param("beforeMonth") String beforeMonth);
}
