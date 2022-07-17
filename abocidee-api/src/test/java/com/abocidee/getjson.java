package com.abocidee;

import com.abocidee.Domain.User;
import com.abocidee.servlet.tools.MyClient;
import com.abocidee.servlet.tools.urls;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class getjson {
    @Autowired
    private static MyClient myClient;

    public static void 用户信息(String cookie) {
        JSONObject userinfo = myClient.get(urls.用户信息url(), cookie);
        User user = new User();
    }
}
