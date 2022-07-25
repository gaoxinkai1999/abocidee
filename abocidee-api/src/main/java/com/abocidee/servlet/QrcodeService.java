package com.abocidee.servlet;

import com.abocidee.servlet.tools.MyClient;
import com.abocidee.servlet.tools.MyError;
import com.abocidee.servlet.tools.MyJson;
import com.abocidee.servlet.tools.Parse;
import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.http.ForestCookie;
import com.dtflys.forest.http.ForestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 该类提供二维码相关服务
 */
@Service
public class QrcodeService {
    @Autowired
    private MyClient myClient;

    public MyJson getqrcode() {
        long time = new Date().getTime();
        //        //获取二维码图片
        String url = "https://passport.baidu.com/v2/api/getqrcode";
        String data = "lp=pc" +
                "&qrloginfrom=pc" +
                "&gid=E2841B1-D82E-4F16-BEA9-CA13F8BEE144" +
                "&oauthLog=" +
                "&callback=tangram_guid_" + time +
                "&apiver=v3&tt=" + new Date().getTime() +
                "&tpl=crt" +
                "&logPage=login" +
                "&_=" + new Date().getTime();
        String s = myClient.demoget(url, data);
        JSONObject json = Parse.getJson(s);
        String imgurl = json.getString("imgurl");
        String sign = json.getString("sign");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imgurl", "https://" + imgurl);
        jsonObject.put("sign", sign);
        jsonObject.put("time", time);
        return MyJson.success(jsonObject);
    }

    public MyJson checkstate(String sign, String time) {

        String url = "https://passport.baidu.com/channel/unicast";

        String result = null;
        String bduss = null;
        for (int i = 0; i < 10; i++) {
            String data = "channel_id=" + sign +
                    "&gid=E2841B1-D82E-4F16-BEA9-CA13F8BEE144" +
                    "&tpl=crt" +
                    "&_sdkFrom=1" +
                    "&callback=tangram_guid_" + time +
                    "&apiver=v3" +
                    "&tt=" + new Date().getTime() +
                    "&_=" + new Date().getTime();
            try {
                result = myClient.demoget(url, data);
            } catch (ForestRuntimeException ex) {

            }
            if (result != null) {
                JSONObject json1 = Parse.getJson(result);
                JSONObject channel_v = json1.getJSONObject("channel_v");
                String status = channel_v.getString("status");
                if (status.equals("0")) {
                    bduss = channel_v.getString("v");
                    break;
                }

            }

        }
        if (bduss == null) {
            return MyJson.error(MyError.超时);
        }
        String url2 = "https://passport.baidu.com/v2/api/bdusslogin?bduss=" + bduss + "&u=https%3A%2F%2Ftest.baidu.com%2F&qrcode=1&tpl=crt&apiver=v3&tt=" + new Date().getTime() + "&traceid=&time=" + (new Date().getTime() / 1000) + "&alg=v3&sig=VDZVaEdIMWFGK09lc3NyU29iNzdReWJkd2RDUEI2OTkzOVB5a3l0TGN2bWNQR0IyL1FRV2kveEFrU3FGeFNxag==&elapsed=2&shaOne=00a70c1b0932649b1cca571546b53fd9d0810c7f&rinfo={\"fuid\":\"612e9ac0dc2a2300d699be661e8c230c\"}&callback=bd__cbs__y0djxj";
        ForestResponse responseget = myClient.responseget(url2);
        ForestCookie bduss1 = responseget.getCookie("BDUSS");
        String value = bduss1.getValue();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cookie",value);
        return MyJson.success("扫码成功,请稍等",jsonObject);

    }

}
