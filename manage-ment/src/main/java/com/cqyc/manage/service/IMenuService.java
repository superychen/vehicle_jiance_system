package com.cqyc.manage.service;

import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cqyc.manage.domain.vo.MenuVo;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author cqyc
 * @since 2019-10-15
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 查询菜单
     * todo 根据登录的用户拥有的角色查询菜单栏
     *
     * @param token 用户的数据
     * @return json
     */
    CommEntity allMenus(String token);

    /**
     * 分页查询所有管理的菜单信息
     *
     * @param pageNo   当前页
     * @param pageSize 当前的页数
     * @return menu
     */
    CommEntity allManageMenus(Integer pageNo, Integer pageSize);

    /**
     * 根据菜单的等级去查询
     *
     * @param level 等级
     * @return 菜单信息
     */
    CommEntity manageMenuLevel(Integer level);

    /**
     * 添加一条菜单信息
     *
     * @param menu 菜单vo
     * @return 成功失败
     */
    CommEntity addMenu(MenuVo menu);

    /**
     * 根据菜单id查询对应的角色
     *
     * @param mid 菜单id
     * @return 对应的角色id
     */
    CommEntity roleMenuWithMid(Integer mid);

    /**
     * 修改菜单信息
     *
     * @param menu 菜单信息
     * @return 是否修改成功
     */
    CommEntity updateMenu(MenuVo menu);

    /**
     * 删除菜单信息
     *
     * @param mids 菜单ids
     * @return 删除是否成功
     */
    CommEntity deleteMenu(Integer[] mids);
}
