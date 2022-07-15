package com.abocidee.newdemo;

import com.dtflys.forest.exceptions.ForestHandlerException;
import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.Interceptor;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class ErrorInterceptor implements Interceptor<String> {

    private final static Logger log = LoggerFactory.getLogger(ErrorInterceptor.class);

    @Override
    public void onError(ForestRuntimeException ex, ForestRequest request, ForestResponse response) {
        System.out.println("错误拦截器调用了");
        System.out.println("异常信息:"+ex.getMessage());
        int status = response.getStatusCode(); // 获取请求响应状态码
        String content = response.getContent(); // 获取请求的响应内容
        Object result = response.getResult(); // 获取方法返回类型对应的返回数据结果
        System.out.println("请求响应状态码:"+status+"\n"+"请求的响应内容:"+content+"\n"+"结果:"+result);
    }
}
