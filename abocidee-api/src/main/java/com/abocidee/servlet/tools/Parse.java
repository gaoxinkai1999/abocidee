package com.abocidee.servlet.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * 该类用于解析特殊响应格式，返回一个json格式对象
 */
@Component
public class Parse {
    public static JSONObject getJson(String text) {
        String[] split1 = text.split("\\(");
        String[] split2 = split1[1].split("\\)");
        String s1 = split2[0];
        return JSON.parseObject(s1);
    }
}
