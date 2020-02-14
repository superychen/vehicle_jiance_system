package com.cqyc.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author cqyc
 * @since 2019-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    @NotBlank(message = "权限不能为空")
    @Length(min = 3, max = 16, message = "权限名称长度必须大于3小于16")
    private String name;

    @NotBlank(message = "权限描述不能为空")
    @Length(min = 1, max = 30, message = "权限描述长度必须大于2小于30")
    private String descript;


    @Override
    protected Serializable pkVal() {
        return this.pid;
    }

}
