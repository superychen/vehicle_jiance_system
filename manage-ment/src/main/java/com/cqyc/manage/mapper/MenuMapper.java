package com.cqyc.manage.mapper;

import com.cqyc.manage.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author cqyc
 * @since 2019-10-15
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询用户的菜单栏
     * @param roleIds 当前登录用户的角色id
     * @return menu
     */
    List<Menu> allMenus(List<Integer> roleIds);


    /**
     * 插入菜单角色表
     * @param mid 菜单id
     * @param rid 角色id
     * @return int
     */
    int addMenuRole(@Param("mid") Integer mid,@Param("rid") Integer rid);

    /**
     * 根据菜单id查询对应的角色
     * @param mid 菜单id
     * @return 角色id
     */
    List<Integer> selectMenuRole(@Param("mid") Integer mid);

    /**
     * 删除对应的菜单id
     * @param mid 菜单id
     * @return int
     */
    int deleteMenuRole(Integer mid);

    /**
     * 删除对应角色
     * @param rids 角色ｉｄ
     * @return comm
     */
    int deleteRole(@Param("rids") List<Integer> rids);
}
