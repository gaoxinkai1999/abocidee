package com.abocidee.controller;


import com.abocidee.servlet.ActionServlet;
import com.abocidee.servlet.tools.MyJson;
import com.abocidee.servlet.tools.当前时间;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



//解决跨域
//@CrossOrigin
//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/action")
@Slf4j
public class ActionController {
    @Autowired
    private ActionServlet actionServlet;

    @RequestMapping(value = "/move")
    public MyJson move(@CookieValue(value = "username",
            defaultValue = "null") String username, String unionname,Boolean onlyexit) {
        if (username.equals("null")) {

        } else {
            log.info(当前时间.get() + "\t" + username + "\t转会到\t" + unionname);
            return actionServlet.move(username, unionname,onlyexit);
        }

        return null;
    }

    @RequestMapping(value = "/select")
    public MyJson select(@CookieValue(value = "username",
            defaultValue = "null") String username, String unionname, String markid) {
        if (!username.equals("null")) {


            log.info(当前时间.get() + "\t" + username + "\t查询\t" + unionname + "\t" + markid);
            MyJson result = actionServlet.select(unionname, markid);
            return result;
        }

        return null;
    }


}
