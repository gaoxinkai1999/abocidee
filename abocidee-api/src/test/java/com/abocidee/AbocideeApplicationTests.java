package com.abocidee;

import com.abocidee.servlet.UnionServlet;
import com.abocidee.servlet.tools.MyClient;
import com.abocidee.servlet.tools.MyJson;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AbocideeApplicationTests {
    @Autowired
    private UnionServlet unionServlet;
    @Autowired
    private MyClient myClient;
    @Test
    void contextLoads() {
        MyJson add = unionServlet.add("148634","5KemJ0RVpJRDdQNWNqVExCNmNYSS00WHVBcFVsSkkyUUhWODRxVXppMVFqSGRpRVFBQUFBJCQAAAAAAAAAAAEAAACyIfpxwsPNvtChxaO2~rrFAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFD~T2JQ~09ib2");
        System.out.println(add.getMsg());

    }
}

