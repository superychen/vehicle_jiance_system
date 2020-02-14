package com.cqyc.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author cqyc
 * @since 2019-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableId(value = "mid", type = IdType.AUTO)
    private Integer mid;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String menuTitle;

    /**
     * 菜单访问的路径
     */
    @NotBlank(message = "菜单访问路径不能为空")
    private String menuPath;

    /**
     * 菜单图标
     */
    @NotBlank(message = "菜单图标不能为空")
    private String menuIcon;

    /**
     * 菜单等级，默认为0,表示当前就是父节点
     * 1:表示子节点,以此类推
     */
    @Min(value = 0,message = "输入菜单等级有误")
    @Max(value = 3,message = "输入菜单等级有误")
    private Integer menuLevel;

    /**
     * 菜单的父级id：默认为0，表示最上层的父级，
     * 关联的就是menu的id
     */
    private Integer menuPid;

    /**
     * 表示该属性不为数据库表字段，但又是必须使用的
     */
    @TableField(exist = false)
    List<Role> roles = new ArrayList<>();

    @TableField(exist = false)
    private Object children;

    @Override
    protected Serializable pkVal() {
        return this.mid;
    }

}
