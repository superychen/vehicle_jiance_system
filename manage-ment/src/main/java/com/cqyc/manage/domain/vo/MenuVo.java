package com.cqyc.manage.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cqyc.manage.domain.Menu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-15
 */
@Data
public class MenuVo extends Menu {

    @TableField(exist = false)
    private List<Integer> role =  new ArrayList<>();
}
