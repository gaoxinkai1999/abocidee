package com.abocidee;

import com.abocidee.Dao.UnionDao;
import com.abocidee.Dao.UserDao;
import com.abocidee.newdemo.Servlet;
import com.abocidee.newdemo.tools.MyClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AbocideeApplicationTests {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UnionDao unionDao;
    @Autowired
    private MyClient myClient;
    @Autowired
    private Servlet servlet;

    @Test
    void contextLoads() throws InterruptedException {


    }
}

