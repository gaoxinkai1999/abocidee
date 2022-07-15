package com.abocidee.controller;

import com.abocidee.Dao.UnionDao;
import com.abocidee.Dao.UserDao;
import com.abocidee.Domain.Union;
import com.abocidee.Domain.User;
import com.abocidee.newdemo.Servlet;
import com.abocidee.newdemo.tools.当前时间;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
//解决跨域
//@CrossOrigin
//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)

//@RequestMapping
@Slf4j
public class UserController {
    @Autowired
    private Servlet servlet;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UnionDao unionDao;

    @RequestMapping(value = "/zhuanhui")
    @ResponseBody
    public JSONObject zhuanhui(@CookieValue(value = "username",
            defaultValue = "null") String username, String unionname) {
        if (username.equals("null")) {

        } else {
            log.info(当前时间.get() + "\t" + username + "\t转会到\t" + unionname);
            JSONObject result = servlet.move(username, unionname);
            return result;
        }

        return null;
    }

    //    @RequestMapping(value = "/zhuanhuihtml")
//    public String demo1(){
//        return "/zhuce.html";
//    }
    @RequestMapping(value = "/chaxun")
    @ResponseBody
    public JSONObject chaxun(@CookieValue(value = "username",
            defaultValue = "null") String username, String unionname, String markid) {
        if (!username.equals("null")) {


            log.info(当前时间.get() + "\t" + username + "\t查询\t" + unionname + "\t" + markid);
            JSONObject result = servlet.select(unionname, markid);
            return result;
        }

        return null;
    }

    @RequestMapping(value = "/demo")
    @ResponseBody
    public String demo() {
        return "hello";
    }

    @RequestMapping(value = "/xxx")
    @ResponseBody
    public String 登录状态(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userName = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    userName = cookie.getValue();
                    break;
                }
            }
        }
        return userName;
    }


    @RequestMapping(value = "/登录")
    @ResponseBody
    public JSONObject 登录(HttpServletResponse response, String username) {
        User user = userDao.get(username);
        if (user != null) {
            Cookie cookie = new Cookie("username", user.getUsername());
            cookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(cookie);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "success");
            jsonObject.put("username", username);

            return jsonObject;
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "fail");
            jsonObject.put("text","不存在该用户");
            return jsonObject;
        }
    }

    @RequestMapping(value = "/注册")
    @ResponseBody
    public JSONObject 注册(HttpServletResponse response, String cookie) {
        JSONObject jsonObject = servlet.useradd(cookie);
        if (jsonObject.getString("msg").equals("success")) {
            Cookie cookie1 = new Cookie("username", jsonObject.getString("username"));
            response.addCookie(cookie1);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/退出登录")
    @ResponseBody
    public void 退出登录(@CookieValue(value = "username",
            defaultValue = "null") String username, HttpServletResponse response) {
        Cookie cookie = new Cookie("username", null);
        cookie.setMaxAge(0);
        cookie.setValue(null);
        response.addCookie(cookie);
    }

    @RequestMapping(value = "/setcookie")
    @ResponseBody
    public void setcookie(@CookieValue(value = "username",
            defaultValue = "null") String username, String cookie) {
        if (!username.equals("null")) {
            userDao.setcookie(username, cookie);
        }
    }

    @GetMapping(value = "/setunioncookie")
    public String 获得公会页面() {
        return "gm.html";
    }

    @PostMapping(value = "/setunioncookie")
    public void setunioncookie(String unionname, String cookie) {
        unionDao.setCookie(unionname, cookie);
    }

    @RequestMapping(value = "/getunion")
    @ResponseBody
    public JSONObject getunion() {
        ArrayList<Union> all = unionDao.getAll();
        ArrayList<String> unionnames = new ArrayList<>();
        for (Union union : all) {
            unionnames.add(union.getUnionname());
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("unions", unionnames);
        return jsonObject;
    }
}
