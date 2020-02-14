package com.cqyc.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.User;
import com.cqyc.manage.domain.vo.UserVo;
import com.cqyc.manage.feign.SmsFeign;
import com.cqyc.manage.mapper.UserMapper;
import com.cqyc.manage.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2019-10-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SmsFeign smsFeign;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final Integer USERNAME_MIN_LENGTH = 0;
    private static final Integer USERNAME_MAX_LENGTH = 16;

    @Override
    public CommEntity allUser(String username, Integer pageNo, Integer pageSize) {
        IPage<User> userIPage;
        Page<User> userPage = new Page<>(pageNo, pageSize);

        if (StringUtils.isBlank(username)) {
            userIPage = userMapper.selectPage(userPage, null);
        } else {
            if (StringUtils.length(username) <= USERNAME_MIN_LENGTH || StringUtils.length(username) > USERNAME_MAX_LENGTH) {
                return CommEntity.create("输入的用户名长度有误", 500);
            }
            userIPage = userMapper.selectPage(userPage, new QueryWrapper<User>().lambda().like(User::getUsername, username));
        }
        return CommEntity.create(userIPage, 200);
    }


    @Override
    public CommEntity oneUser(Integer uid) {
        User user = userMapper.getOneUserRole(uid);
//        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUid, uid));
        if (user == null) {
            return CommEntity.create("用户查找失败", 500);
        }
        return CommEntity.create(user, 200);
    }


    /**
     * 这里不能用事务,只能做补偿
     *
     * @param user 用户
     * @return
     */
    @Override
    public CommEntity addUser(UserVo user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int insert = userMapper.insert(user);
        //需要用户id,需要查询
        User selectUser = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, user.getUsername()));
        int res = userMapper.insertUserRole(selectUser.getUid(), user.getRoleIds());
        if (insert != 1 || res < 0) {
            return CommEntity.create("用户插入失败", 500);
        }
        return CommEntity.create(insert, 200);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommEntity updateUser(UserVo user) {
        if (user.getUid() == null) {
            return CommEntity.create("无法识别修改的用户", 500);
        }
        if (StringUtils.isNotBlank(user.getPassword())) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        int i = userMapper.updateById(user);
        int delRes = userMapper.deleteUserRole(user.getUid());
        int i1 = userMapper.insertUserRole(user.getUid(), user.getRoleIds());
        if (i != 1 || delRes < 0 || i1 < 0) {
            return CommEntity.create("修改时发生未知错误", 500);
        }
        return CommEntity.create("修改成功", 200);
    }

    @Override
    public CommEntity deleteUser(Integer[] ids) {
        int i = userMapper.deleteBatchIds(Arrays.asList(ids));
        if (i == 0) {
            return CommEntity.create("删除时发生错误", 500);
        }
        return CommEntity.create("删除成功", 200);
    }

    /**
     * 发送短信验证码
     */
    @Override
    public CommEntity sendSms(String telephone) {
        String phoneCode = (String) redisTemplate.opsForHash().get("phoneCode", telephone);
        Long phoneCodeExpireTime = redisTemplate.getExpire("phoneCode");
        Long expireTime = 240L;
        if (phoneCodeExpireTime >= expireTime) {
            if (StringUtils.isNotBlank(phoneCode)) {
                return CommEntity.create("请稍后再次发送短信验证码", 500);
            }
        }
        CommEntity commEntity = smsFeign.testSms(telephone);
        Integer smsCodeStatus = 200;
        if (!commEntity.getCode().equals(smsCodeStatus)) {
            return CommEntity.create("发送短信验证码失败,请稍后重试", 500);
        }
        return commEntity;
    }
}
