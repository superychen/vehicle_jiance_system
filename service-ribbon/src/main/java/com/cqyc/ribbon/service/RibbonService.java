package com.cqyc.ribbon.service;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-12
 */
public interface RibbonService {

    /**
     * 通过ribbon去调用服务
     * @param name 参数
     * @return
     */
    String ribbonHi(String name);
}
