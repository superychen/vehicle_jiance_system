package com.cqyc.manage.controller;


import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.Menu;
import com.cqyc.manage.domain.vo.MenuVo;
import com.cqyc.manage.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2019-10-15
 */
@RestController
@RequestMapping("/manage")
@Slf4j
public class MenuController {

    @Autowired
    private IMenuService menuService;

    /**
     * 查询所有首页中所有的菜单
     */
    @GetMapping("/menu")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOY')")
    public CommEntity allMenus(@RequestHeader("Authorization") String token) {
        return menuService.allMenus(token);
    }

    /**
     * 菜单管理：查询所有的菜单信息
     */
    @GetMapping("/menus")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity allManageMenus(@RequestParam("pageNo") Integer pageNo,
                                     @RequestParam("pageSize") Integer pageSize) {
        return menuService.allManageMenus(pageNo, pageSize);
    }

    @GetMapping("/menu/{level}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity manageMenuLevel(@PathVariable("level") Integer level) {
        return menuService.manageMenuLevel(level);
    }

    /**
     * 新增一个菜单
     */
    @PostMapping("/menu")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity addMenu(@RequestBody @Validated MenuVo menu, BindingResult result) {
        if (result.hasFieldErrors()) {
            log.info("错误原因--->{}", result.getFieldErrors());
            return CommEntity.create("输入的内容不能为空", 500);
        }
        return menuService.addMenu(menu);
    }

    @GetMapping("/roleMenu/{mid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity roleMenuWithMid(@PathVariable Integer mid) {
        if (mid == null || mid == 0) {
            return CommEntity.create("菜单的id有误", 500);
        }
        return menuService.roleMenuWithMid(mid);
    }

    /**
     * 修改菜单信息
     */
    @PutMapping("/menu")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity updateMenu(@RequestBody @Validated MenuVo menu, BindingResult result) {
        if (result.hasFieldErrors()) {
            log.info("错误原因--->{}", result.getFieldErrors());
            return CommEntity.create("输入的内容不能为空", 500);
        }
        return menuService.updateMenu(menu);
    }

    /**
     * 删除菜单信息
     */
    @DeleteMapping("/menu")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity deleteMenu(@RequestBody Integer[] mids) {
        if (mids.length <= 0) {
            return CommEntity.create("菜单删除获取的id不能为空", 500);
        }
        return menuService.deleteMenu(mids);
    }
}
