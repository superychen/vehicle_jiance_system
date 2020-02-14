package com.cqyc.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.Menu;
import com.cqyc.manage.domain.Role;
import com.cqyc.manage.domain.User;
import com.cqyc.manage.domain.vo.MenuVo;
import com.cqyc.manage.feign.LoginFeign;
import com.cqyc.manage.mapper.MenuMapper;
import com.cqyc.manage.mapper.UserMapper;
import com.cqyc.manage.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2019-10-15
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private LoginFeign loginFeign;

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询菜单栏
     */
    @Override
    public CommEntity allMenus(String token) {
        //todo 这里先模拟当前登录用户的角色权限
        List<Integer> roleIds = new ArrayList<>();
        User userInfo = userinfo(token);
        User user = userMapper.getOneUserRole(userInfo.getUid());
        user.getRoles().forEach(res -> roleIds.add(res.getRid()));
        List<Menu> menus = menuMapper.allMenus(roleIds);

        if (CollectionUtils.isEmpty(menus)) {
            return CommEntity.create("未查询到菜单", 500);
        }
        List<Menu> listMenu = new ArrayList<>();
        for (Menu menu : menus) {
            List<Menu> listMenu2 = new ArrayList<>();
            menus.forEach((menu1) -> {
                if (menu.getMid().equals(menu1.getMenuPid())) {
                    listMenu2.add(menu1);
                }
            });
            menu.setChildren(listMenu2);
            if (menu.getMenuPid() == 0) {
                listMenu.add(menu);
            }
        }
        return CommEntity.create(listMenu, 200);
    }

    /**
     * 查询管理的菜单信息
     *
     * @param pageNo   当前页
     * @param pageSize 当前的页数
     * @return menu
     */
    @Override
    public CommEntity allManageMenus(Integer pageNo, Integer pageSize) {
        IPage<Menu> userIPage;
        Page<Menu> userPage = new Page<>(pageNo, pageSize);
        userIPage = menuMapper.selectPage(userPage, null);
        log.info("userIPage-->  {}", userIPage);
        return CommEntity.create(userIPage, 200);
    }

    /**
     * 根据菜单的等级去查询
     */
    @Override
    public CommEntity manageMenuLevel(Integer level) {
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().lambda().eq(Menu::getMenuLevel, level));
        if (CollectionUtils.isEmpty(menus)) {
            return CommEntity.create("返回的菜单栏节点为空", 100);
        }
        return CommEntity.create(menus, 200);
    }

    /**
     * 新增菜单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommEntity addMenu(MenuVo menu) {
        int insert = menuMapper.insert(menu);
        if (insert != 1) {
            return CommEntity.create("创建菜单失败", 500);
        }
        List<Integer> role = menu.getRole();
        //如果选择的角色
        if (CollectionUtils.isNotEmpty(role)) {
            role.forEach(rid -> {
                menuMapper.addMenuRole(menu.getMid(), rid);
            });
        }
        return CommEntity.create(insert, 200);
    }

    /**
     * 根据菜单id查询对应的角色
     */
    @Override
    public CommEntity roleMenuWithMid(Integer mid) {
        List<Integer> rids = menuMapper.selectMenuRole(mid);
        return CommEntity.create(rids, 200);
    }

    /**
     * 修改菜单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommEntity updateMenu(MenuVo menu) {
        int updateRes = menuMapper.updateById(menu);
        if (updateRes != 1) {
            return CommEntity.create("修改菜单失败", 500);
        }
        //先把对应的菜单的中间表数据删除
        List<Integer> rids = menuMapper.selectMenuRole(menu.getMid());
        //查询出来有对应的id对应，则先删除
        if (CollectionUtils.isNotEmpty(rids)) {
            menuMapper.deleteMenuRole(menu.getMid());
        }
        List<Integer> role = menu.getRole();
        //添加数据
        if (CollectionUtils.isNotEmpty(role)) {
            role.forEach(rid -> {
                menuMapper.addMenuRole(menu.getMid(), rid);
            });
        }
        return CommEntity.create(updateRes, 200);
    }

    /**
     * 删除菜单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommEntity deleteMenu(Integer[] mids) {
        int delRes = menuMapper.deleteBatchIds(Arrays.asList(mids));
        if (delRes == 0) {
            return CommEntity.create("删除时发生错误", 500);
        }
        //循环删除菜单角色对应的信息
        for (int i = 0; i < mids.length; i++) {
            menuMapper.deleteMenuRole(mids[i]);
        }
        return CommEntity.create(delRes, 200);
    }


    /**
     * 用户信息
     *
     * @param token
     * @return
     */
    public User userinfo(String token) {
        CommEntity commEntity = loginFeign.userInfo(token);
        //强转为用户,注意出错
        if (commEntity.getCode() != 200) {
            throw new RuntimeException("服务器出现异常");
        }
        String jsonStr = JSONObject.toJSONString(commEntity.getData());
        log.info("转换的用户信息---->{}", jsonStr);
        User user = JSONObject.parseObject(jsonStr, User.class);
        return user;
    }
}
