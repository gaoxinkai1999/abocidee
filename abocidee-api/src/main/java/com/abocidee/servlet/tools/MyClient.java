package com.abocidee.servlet.tools;

import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.annotation.DecompressGzip;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Request;
import com.dtflys.forest.http.ForestResponse;
import org.springframework.stereotype.Controller;

import java.util.concurrent.Future;

@DecompressGzip
@Controller
public interface MyClient {
    /**
     * 常规get请求
     * @param url
     * @param cookie
     * @return
     */

    @Request(
            url = "{0}",
            dataType = "json",
            headers = {"Accept: text/plain",

                    "Cookie:BDUSS={1}"}
    )
    JSONObject get(String url, String cookie);

    /**
     * 默认body格式为 application/x-www-form-urlencoded，即以表单形式序列化数据
     */
    @Post(
            url = "https://test.baidu.com/mark/qualityTest/getCheckPageList",
            headers = {"Accept:text/plain",
                    "Cookie: YII_CSRF_TOKEN=9e22c8bb046e2dd4fe7c44c729c7cf3a12444703;BDUSS={0}; BDUSS_BFESS={0};"},
            dataType = "json",
            data = "markId={1}&filter%5BpageStatus%5D=&filter%5BenumFitQualityStatus%5D=&filter%5BenumFitVerifyStatus%5D=&filter%5BenumQualityVerifyStatus%5D=&filter%5BverifyRound%5D=&filter%5BminPageId%5D=&filter%5BmaxPageId%5D=&filter%5BminAgreeRate%5D=&filter%5BmaxAgreeRate%5D=&filter%5BminDoneNum%5D=&filter%5BmaxDoneNum%5D=&filter%5BmarkUserName%5D=&filter%5Bcontent%5D=&filter%5BqualityUserName%5D=&filter%5BanswerType%5D=final&order%5Bcolumn%5D=page_id&order%5Bvalue%5D=asc&limit%5Bstart%5D=0&limit%5Bend%5D=10&refresh_cache=1&YII_CSRF_TOKEN=9e22c8bb046e2dd4fe7c44c729c7cf3a12444703&x_a=1"
    )
    JSONObject 查询质检包(String cookie, String markId);

    /**
     * 常规post请求
     * @param url
     * @param cookie
     * @param data
     * @return
     */
    @Post(
            url = "{0}",
            headers = {"Accept:text/plain",
                    "Cookie: YII_CSRF_TOKEN=9e22c8bb046e2dd4fe7c44c729c7cf3a12444703;BDUSS={1}; BDUSS_BFESS={1};"},
            dataType = "json",
            data = "{2}"
    )
    JSONObject post(String url, String cookie, String data);
    /**
     * 异步get请求,无法通过return接收响应，future.get
     * @param url
     * @param cookie
     * @return
     */

    @Request(
            url = "{0}",
            dataType = "json",
            async = true,
            headers = {"Accept: text/plain",

                    "Cookie:BDUSS={1}"}
    )
    Future<JSONObject> asyncget(String url, String cookie);


    @Post(
            url = "{0}",
            headers = {"Accept:text/plain",
                    "Cookie: YII_CSRF_TOKEN=9e22c8bb046e2dd4fe7c44c729c7cf3a12444703;BDUSS={1}; BDUSS_BFESS={1};"},
            dataType = "json",
            data = "{2}"
    )
    String demopost(String url, String cookie, String data);

    @Request(
            url = "{0}",
            dataType = "json",
            headers = {"Accept: text/plain",

                    }
    )
    JSONObject get(String url);

    @Request(
            url = "{0}",
//            dataType = "json",
            headers = {"Accept: text/plain",

            },
            data = "{1}"
    )
    String demoget(String url,String data);

    @Request(
            url = "{0}",
//            dataType = "json",
            headers = {"Accept: text/plain",
            }
    )
    ForestResponse responseget(String url);
    @Request(
            url = "{0}",
            dataType = "json",
            headers = {"Accept: text/plain",

                    "Cookie:BDUSS={1}"}
    )
    String Stringget(String url, String cookie);
}
