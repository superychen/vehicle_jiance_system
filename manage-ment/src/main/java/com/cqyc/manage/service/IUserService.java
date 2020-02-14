package com.cqyc.manage.service;

import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cqyc.manage.domain.vo.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cqyc
 * @since 2019-10-14
 */
public interface IUserService extends IService<User> {

    /**
     * 查询全部的用户，或者根据用户名查询
     * @param username 用户名
     * @return 用户
     */
    CommEntity allUser(String username, Integer pageNo, Integer pageSize);

    /**
     * 根据uid查询用户
     * @param uid id
     * @return user
     */
    CommEntity oneUser(Integer uid);

    /**
     * 添加登录用户
     * @param user 用户
     * @return true
     */
    CommEntity addUser(UserVo user);

    /**
     * 根据id修改用户
     * @param user
     * @return
     */
    CommEntity updateUser(UserVo user);

    /**
     * 删除用户
     * @param ids
     * @return
     */
    CommEntity deleteUser(Integer[] ids);

    /**
     * 发送短信验证码
     * @param telephone 电话
     * @return commEntity
     */
    CommEntity sendSms(String telephone);
}
