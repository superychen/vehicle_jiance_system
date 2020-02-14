package com.cqyc.manage.domain.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: cqyc
 * Description: 修改pdf时需要的vo包，往pdf添加图片
 * Created by cqyc on 19-12-23
 */
@Data
public class PdfUploadVo implements Serializable {
    /**
     * 　源文件pdf的路径
     */
    @NotBlank(message = "pdf路径不能为空")
    private String fileId;

    /**
     * 图片的路径
     */
    @NotBlank(message = "图片路径不能为空")
    private String fileImg;

    /**
     * 　当前pdf处于的页数
     */
    @Min(value = 1, message = "当前pdf页数小于１")
    private Integer pageNo;

    /**
     * 　偏移量Ｘ
     */
    @Min(value = 0,message = "X轴偏移量不能为空")
    private Float offsetX;

    /**
     * 　偏移量Ｙ
     */
    @Min(value = 0,message = "Y轴偏移量不能为空")
    private Float offsetY;
}
