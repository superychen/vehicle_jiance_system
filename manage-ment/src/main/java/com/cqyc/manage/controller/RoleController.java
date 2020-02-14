package com.cqyc.manage.controller;


import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.vo.RoleVo;
import com.cqyc.manage.service.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2019-10-15
 */
@RestController
@RequestMapping("/manage")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/role")
    public CommEntity allRole() {
        return roleService.allRole();
    }

    /**
     * 角色管理中所有的角色
     */
    @GetMapping("/roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity allRoleManage(@RequestParam(value = "rname", required = false) String rname,
                                    @RequestParam("pageNo") Integer pageNo,
                                    @RequestParam("pageSize") Integer pageSize) {

        return roleService.allRoleManage(rname, pageNo, pageSize);
    }

    /**
     * 新增角色
     */
    @PostMapping("/role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity addRole(@RequestBody RoleVo roleVo) {
        if (roleVo.getPid().length <= 0 || StringUtils.isBlank(roleVo.getRname())) {
            return CommEntity.create("新增角色得到的信息为空,新增失败", 500);
        }
        return roleService.addRole(roleVo.getPid(), roleVo.getRname());
    }


    /**
     * 修改角色
     */
    @PutMapping("/role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity updateRole(@RequestBody RoleVo roleVo) {
        if (roleVo.getRid() == null || roleVo.getPid().length <= 0 || StringUtils.isBlank(roleVo.getRname())) {
            return CommEntity.create("修改角色得到的信息为空,新增失败", 500);
        }
        return roleService.updateRole(roleVo);
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity deleteRole(@RequestBody Integer[] rids) {
        if (rids.length <= 0) {
            return CommEntity.create("菜单删除获取的id不能为空", 500);
        }
        return roleService.deleteRole(rids);
    }

}
