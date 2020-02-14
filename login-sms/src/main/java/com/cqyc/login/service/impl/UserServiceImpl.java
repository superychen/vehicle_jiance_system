package com.cqyc.login.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqyc.login.comm.util.Md5Hash;
import com.cqyc.login.comm.util.jwt.JwtUtils;
import com.cqyc.login.domain.CommEntity;
import com.cqyc.login.domain.JWT;
import com.cqyc.login.domain.User;
import com.cqyc.login.domain.vo.UserVo;
import com.cqyc.login.feign.AuthServiceClient;
import com.cqyc.login.mapper.UserMapper;
import com.cqyc.login.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2019-10-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AuthServiceClient client;

    /**
     * 使用jwt生成token保存
     */
    @Override
    public CommEntity login(String username, String password, String imgUuid, String imgCode) {
        Object imgCodeYan = redisTemplate.opsForHash().get("imgCode", imgUuid);
        if (imgCodeYan == null) {
            return CommEntity.create("验证码失效,请重新点击验证", 500);
        }
        String imgCodeStr = imgCodeYan.toString();
        if (!StringUtils.equals(imgCodeStr.toUpperCase(), imgCode.toUpperCase())) {
            return CommEntity.create("输入的图片验证码不正确", 500);
        }
        JWT jwt = client.postAccessToken("Basic Y3F5YzoxMjM0NTY=", "password", username, password);
        if (jwt == null) {
            return CommEntity.create("用户名或密码输入错误", 500);
        }
        //将用户信息暂时存到缓存中，包括密码(用于刷新jwt token),过期时间为30分钟
        redisTemplate.opsForValue().set(username + ":token", jwt.getAccess_token(), 1800, TimeUnit.SECONDS);
        UserVo userVo = new UserVo();
        userVo.setJwt(jwt);
        userVo.setUsername(username);
        return CommEntity.create(userVo, 200);
    }

    /**
     * 刷新token
     */
    @Override
    public CommEntity refreshToken(String username) {
        HashMap<String, Object> map = new HashMap<>(1);
        String token = redisTemplate.opsForValue().get(username);
        if (StringUtils.isBlank(token)) {
            return CommEntity.create("用户信息已过期,请重新登录", 500);
        }
        //生成一个新的token
        String newToken = JwtUtils.generateToken(username);
        redisTemplate.opsForValue().set(username, newToken, JwtUtils.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        map.put("token", newToken);
        return CommEntity.create(map, 200);
    }


    /**
     * 修改用户信息
     */
    @Override
    public CommEntity updateUserInfo(User user) {
        try {
            if (StringUtils.isNotBlank(user.getPassword())) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            Boolean hasKey = redisTemplate.hasKey(user.getUsername() + ":userInfo");
            int i = userMapper.updateById(user);
            if (i != 1) {
                return CommEntity.create("用户修改出现异常", 500);
            }
            if (hasKey != null && hasKey) {
                user.setPassword("");
                String jsonUser = JSONObject.toJSONString(user);
                redisTemplate.opsForValue().set(user.getUsername() + ":userInfo", jsonUser, 60 * 60 * 2, TimeUnit.SECONDS);
            }
            return CommEntity.create("用户修改成功", 200);
        } catch (Exception e) {
            return CommEntity.create("用户格式转换错误", 500);
        }

    }

    /**
     * 修改用户密码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommEntity updatePassword(String username, String password, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode(password));
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username).eq(User::getPassword, passwordEncoder.encode(password)));
        if (user == null) {
            return CommEntity.create("输入的密码有误,请重新输入", 500);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        int i = userMapper.updateById(user);
        if (i != 1) {
            return CommEntity.create("修改用户失败,请稍后再次尝试", 500);
        }
        return CommEntity.create(i, 200);
    }

    /**
     * 判断当前用户是否被注册
     *
     * @param username 　用户名
     * @return
     */
    @Override
    public CommEntity isRegisterUser(String username) {
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
        if (user == null) {
            return CommEntity.create("该账户可用", 200);
        }
        return CommEntity.create("该账户已经被注册,请重新填写", 500);
    }

    /**
     * 注册,暂时不考虑分布式事务
     *
     * @return
     */
    @Override
    public CommEntity register(UserVo user) {
        String phoneCode = redisTemplate.opsForValue().get(user.getTelephone() + ":phoneCode");
        if (!StringUtils.equals(user.getTelephoneCode(), phoneCode)) {
            return CommEntity.create("手机验证码输入错误", 500);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user.setUserImg("group1/M00/00/00/wKgrDV2-jeuAW0ezAAAVxDXzlUw820_150x150.jpg");
            int i = userMapper.insert(user);
            //１为管理员，2为普通用户,不得修改
            User selUser = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, user.getUsername()));
            userMapper.insertUserRole(selUser.getUid(), 2);
            if (i != 1) {
                throw new RuntimeException("注册失败,请仔细检查");
            }
            return CommEntity.create(i, 200);
        } catch (Exception e) {
            throw new RuntimeException("服务器出现异常,请及时联系管理员");
        }
    }
}
