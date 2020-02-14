package com.cqyc.manage.controller;


import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.Permission;
import com.cqyc.manage.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2019-11-05
 */
@RestController
@RequestMapping("/manage/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    /**
     * 查询权限,如果rid为空,则查询全部权限
     *
     * @param rid
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity allPermission(@RequestParam(value = "rid", required = false) Integer rid) {
        if (rid == null) {
            List<Permission> list = permissionService.list();
            return CommEntity.create(list, 200);
        } else {
            return permissionService.selectPermission(rid);
        }
    }

    @GetMapping("all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity pageAllPermission(@RequestParam("pageNo") Integer pageNo,
                                        @RequestParam("pageSize") Integer pageSize) {
        if (pageSize <= 0 || pageNo <= 0) {
            return CommEntity.create("无法进行查询，没有数据", 500);
        }
        return permissionService.pageAllPermission(pageNo, pageSize);
    }


    /**
     * 新增权限
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity addPermission(@RequestBody @Validated Permission permission, BindingResult result) {
        if (result.hasFieldErrors()) {
            return CommEntity.create(result.getAllErrors().get(0).getDefaultMessage(), 500);
        }
        return permissionService.addPermission(permission);
    }

    /**
     * 删除权限,权限只能一个一个的删除
     */
    @DeleteMapping("one")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity deletePermissionOne(@RequestParam("pid") Integer pid) {
        if (pid <= 0) {
            return CommEntity.create("未查询到对应的权限", 500);
        }
        return permissionService.deletePermissionOne(pid);
    }

    @DeleteMapping("more")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommEntity deletePermissionMore(@RequestBody Integer[] pids) {
        if (pids.length <= 0) {
            return CommEntity.create("未查询到对应的权限", 500);
        }
        return permissionService.deletePermissionMore(pids);
    }

}
