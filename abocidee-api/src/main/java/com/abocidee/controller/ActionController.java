package com.abocidee.controller;


import com.abocidee.servlet.ActionServlet;
import com.abocidee.servlet.tools.MyJson;
import com.abocidee.servlet.tools.当前时间;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/action")
@Slf4j
public class ActionController {
    @Autowired
    private ActionServlet actionServlet;

    @RequestMapping(value = "/move")
    public MyJson move(@CookieValue(value = "username") String username, String unionname, Boolean onlyexit) {

        log.info(当前时间.get() + "\t" + username + "\t转会到\t" + unionname);
        return actionServlet.move(username, unionname, onlyexit);

    }

    @RequestMapping(value = "/select")
    public MyJson select(@CookieValue(value = "username") String username, String unionname, String markid) {

        log.info(当前时间.get() + "\t" + username + "\t查询\t" + unionname + "\t" + markid);
        MyJson result = actionServlet.select(unionname, markid);
        return result;

    }


}
