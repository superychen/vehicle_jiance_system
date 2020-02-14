package generator;

import com.cqyc.manage.comm.util.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: cqyc
 * Description:车辆检查正常返回结果
 * {
*     "code":"success",
*     "result":"ok",
*     "msg":"信息匹配成功",
*     "vioCount":3
 * }
 * Created by cqyc on 19-11-20
 */
public class VehicleInfo {
    public static void main(String[] args) {
        String host = "http://wan66.market.alicloudapi.com";
        String path = "/xxjy";
        String method = "GET";
        String appcode = "8fe77df15a7a41f58a16bc2f604b6250";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        //车牌号
        querys.put("cph", "京A888888");
        //车型
        querys.put("cx", "cx");
        //发动机号
        querys.put("fdj", "123456");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
