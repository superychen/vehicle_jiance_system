package com.cqyc.login.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqyc.login.config.rabbitmq.MQConfig;
import com.cqyc.login.domain.CommEntity;
import com.cqyc.login.domain.User;
import com.cqyc.login.domain.WebsiteCount;
import com.cqyc.login.mapper.UserMapper;
import com.cqyc.login.mapper.WebsiteCountMapper;
import com.cqyc.login.service.IWebsiteCountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 网站统计表 服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2020-01-23
 */
@Service
@Slf4j
public class WebsiteCountServiceImpl extends ServiceImpl<WebsiteCountMapper, WebsiteCount> implements IWebsiteCountService {

    @Autowired
    private WebsiteCountMapper websiteCountMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public CommEntity countRegister(LocalDate date, LocalDate beforeMonth) {
        List<WebsiteCount> counts = websiteCountMapper.selectList(new QueryWrapper<WebsiteCount>().lambda()
                .le(WebsiteCount::getWebsiteMonth, date).ge(WebsiteCount::getWebsiteMonth, beforeMonth));
        return CommEntity.create(counts, 200);
    }

    @Override
    public CommEntity userInfo(String userInfoToken) {
        //解码
        Base64 base64 = new Base64();
        try {
            String res = new String(base64.decode(userInfoToken), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(res);
            String userName = jsonObject.getString("user_name");
            Boolean hasKey = redisTemplate.hasKey(userName + ":userInfo");
            if (hasKey == null || !hasKey) {
                User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, userName));
                if (user == null) {
                    return CommEntity.create("用户解析错误,请重新尝试登录", 500);
                }
                //将密码设置为空
                user.setPassword("");
                String userInfo = JSONObject.toJSONString(user);
                //用户查询时,将用户信息缓存2个小时
                redisTemplate.opsForValue().set(userName + ":userInfo", userInfo, 60 * 60 * 2, TimeUnit.SECONDS);
                return CommEntity.create(user, 200);
            } else {
                //走缓存
                String userInfo = redisTemplate.opsForValue().get(userName + ":userInfo");
                return CommEntity.create(JSONObject.parseObject(userInfo, User.class), 200);
            }
        } catch (Exception e) {
            return CommEntity.create("用户解析错误,请重新尝试登录", 500);
        }
    }

    @Override
    public CommEntity isLikeWebsite(Integer isLike, String username) {
        Boolean hasKey = redisTemplate.hasKey(username + ":userInfo");
        if (hasKey == null || !hasKey) {
            return CommEntity.create("获取用户信息失败,请重新尝试登录", 500);
        }
        String res = redisTemplate.opsForValue().get(username + ":userInfo");
        try {
            JSONObject jsonObject = JSONObject.parseObject(res);
            Integer likeWebsite = jsonObject.getInteger("likeWebsite");
            //如果前台传递的是同样的数字
            if (likeWebsite == null || isLike.equals(likeWebsite)) {
                return CommEntity.create("请不要重复的添加或取消点赞哟", 500);
            }
            //修改
            jsonObject.put("likeWebsite", isLike);
            //将修改的用户的点赞写入缓存
            redisTemplate.opsForValue().set(username + ":userInfo", jsonObject.toJSONString(), 60 * 60 * 2, TimeUnit.SECONDS);
            //异步写入数据库
            Map<String, Integer> map = new HashMap<>(2);
            map.put("uid", jsonObject.getInteger("uid"));
            map.put("isLike", isLike);
            rabbitTemplate.convertAndSend(MQConfig.SEND_MESSAGE_EXCHANGE, MQConfig.SEND_MESSAGE_ROUTING_KEY2, map);
            return CommEntity.create(isLike, 200);
        } catch (Exception e) {
            return CommEntity.create("用户信息格式转化出现错误,请联系管理员", 500);
        }
    }
}
