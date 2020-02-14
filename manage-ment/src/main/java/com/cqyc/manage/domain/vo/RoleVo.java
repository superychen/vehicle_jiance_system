package com.cqyc.manage.domain.vo;

import com.cqyc.manage.domain.Role;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-11-13
 */
@Data
public class RoleVo extends Role implements Serializable {
    private Integer[] pid;
}
