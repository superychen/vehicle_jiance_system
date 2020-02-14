package com.cqyc.vehicle;

import com.cqyc.vehicle.comm.EasyPoiUtil;
import com.cqyc.vehicle.domain.bo.VehicleResultBo;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-2-13
 */

public class EasyPoiTest {
    @Test
    public void test1() {
        ArrayList<VehicleResultBo> list = new ArrayList<>();
        VehicleResultBo resultBo = new VehicleResultBo();
        resultBo.setId(1);
        resultBo.setConformity(1);
        resultBo.setConformityText("合格");
        resultBo.setIsConformityText(75);
        list.add(resultBo);
//        EasyPoiUtil.exportExcel(list, "粤5D24834", "检测结果",VehicleResultBo.class,"cqyc"+".xls" );
    }
}
