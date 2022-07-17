package com.abocidee.servlet.tools;

import com.dtflys.forest.callback.SuccessWhen;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;

// 自定义成功/失败条件实现类
// 需要实现 SuccessWhen 接口
public class MySuccessCondition implements SuccessWhen {

    /**
     * 请求成功条件
     * @param req Forest请求对象
     * @param res Forest响应对象
     * @return 是否成功，true: 请求成功，false: 请求失败
     */
    @Override
    public boolean successWhen(ForestRequest req, ForestResponse res) {
        // req 为Forest请求对象，即 ForestRequest 类实例
        // res 为Forest响应对象，即 ForestResponse 类实例
        // 返回值为 ture 则表示请求成功，false 表示请求失败
        return res.noException() &&   // 请求过程没有异常
//                res.statusOk() &&     // 并且状态码在 100 ~ 399 范围内
                res.statusIs(200); // 只能是200

    }
}
