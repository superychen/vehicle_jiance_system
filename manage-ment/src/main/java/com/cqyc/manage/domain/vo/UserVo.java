package com.cqyc.manage.domain.vo;

import com.cqyc.manage.domain.User;
import lombok.Data;

import java.util.List;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-2-3
 */
@Data
public class UserVo extends User {
    private List<Integer> roleIds;
}
