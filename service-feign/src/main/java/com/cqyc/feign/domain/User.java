package com.cqyc.feign.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author cqyc
 * @since 2019-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User  extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 返回时不显示密码
     * 过滤字段密码，读取的时候忽略，只允许输入
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "地址不能为空")
    private String address;

    @Length(min = 11,max = 11,message = "手机号码格式有误")
    private String telephone;

    @TableField(exist = false)
    private Set<Role> roles = new HashSet<>();

    /**
     * 用户图片
     */
    private String userImg;


    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

}
