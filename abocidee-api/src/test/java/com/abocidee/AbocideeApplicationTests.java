package com.abocidee;

import com.abocidee.Dao.UserDao;
import com.abocidee.Domain.User;
import com.abocidee.servlet.QrcodeService;
import com.abocidee.servlet.tools.MyClient;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class AbocideeApplicationTests {
    @Autowired
    private QrcodeService qrcodeService;
    @Autowired
    private MyClient myClient;
    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() throws IOException, JSONException {
        User user = userDao.get("demo");
        String url="https://test.baidu.com/crowdtest/n/union/index";
        String html = myClient.Stringget(url, user.getCookie());
        Document doc = Jsoup.parse(html);
        Elements elementsByClass = doc.getElementsByClass("union-sidebar-user-name");
        System.out.println(elementsByClass.isEmpty());
        System.out.println(elementsByClass.text());

    }
}

