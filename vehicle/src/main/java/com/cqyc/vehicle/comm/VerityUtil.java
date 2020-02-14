package com.cqyc.vehicle.comm;

import com.cqyc.vehicle.domain.CommEntity;
import com.cqyc.vehicle.domain.vo.VehicleStepVo;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-2-13
 */
public class VerityUtil {

    public static CommEntity verityStep(VehicleStepVo res) {
        if (res.getConformity() == 1) {
            if (res.getIsConformityText() < res.getStepIndicator()) {
                return CommEntity.create(res.getStepTitle() + "合格率最低为" + res.getIsConformityText() + "%", 500);
            }
        } else if (res.getConformity() == 2) {
            if (StringUtils.isBlank(res.getNotConformtyText())) {
                return CommEntity.create(res.getStepTitle() + "请描述损坏情况!!!", 500);
            } else {
                //不得超过500个字
                if (StringUtils.length(res.getNotConformtyText()) > 500) {
                    return CommEntity.create(res.getStepTitle() + "损坏情况描述不能超过500字!!", 500);
                }
            }
            if (StringUtils.isBlank(res.getNotConformtyPic())) {
                return CommEntity.create(res.getStepTitle() + "请上传未达合格标准的检测图片!!!", 500);
            } else {
                //不得超过500个字
                if (StringUtils.length(res.getNotConformtyText()) > 100) {
                    return CommEntity.create(res.getStepTitle() + "上传图片长度错误!!", 500);
                }
            }
        } else {
            return CommEntity.create(res.getStepTitle() + "未选择结果!!", 500);
        }
        return CommEntity.create("校验成功", 200);
    }
}
