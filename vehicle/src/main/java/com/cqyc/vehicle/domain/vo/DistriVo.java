package com.cqyc.vehicle.domain.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-2-10
 */
@Data
public class DistriVo {

    @NotBlank(message = "预约id不能为空")
    @Min(1)
    private Integer appId;

    private List<String> staffIds;
}
