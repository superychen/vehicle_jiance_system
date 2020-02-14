package com.cqyc.login.service;

import com.cqyc.login.domain.CommEntity;
import com.cqyc.login.domain.WebsiteCount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;

/**
 * <p>
 * 网站统计表 服务类
 * </p>
 *
 * @author cqyc
 * @since 2020-01-23
 */
public interface IWebsiteCountService extends IService<WebsiteCount> {

    /**
     * 统计网站用户注册量
     *
     * @param date        　日期
     * @param beforeMonth 　之前的月份
     * @return
     */
    CommEntity countRegister(LocalDate date, LocalDate beforeMonth);

    /**
     * 获取用户的基本信息
     * @param userInfoToken　用户信息
     * @return　comm
     */
    CommEntity userInfo(String userInfoToken);

    /**
     * 对网站的点赞或取消
     * @param isLike 0: 不喜欢我的网站,　1:喜欢我的网站
     * @param username 用户名
     * @return
     */
    CommEntity isLikeWebsite(Integer isLike, String username);
}
