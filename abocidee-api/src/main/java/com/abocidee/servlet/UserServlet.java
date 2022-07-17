package com.abocidee.servlet;

import com.abocidee.Dao.UserDao;
import com.abocidee.Domain.User;
import com.abocidee.servlet.tools.MyClient;
import com.abocidee.servlet.tools.MyError;
import com.abocidee.servlet.tools.MyJson;
import com.abocidee.servlet.tools.urls;
import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.exceptions.ForestNetworkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class UserServlet {
    @Autowired
    private UserDao userDao;
    @Autowired
    private MyClient myClient;

    public MyJson add(String cookie) {
        try {
            //获取用户当前公会
            JSONObject jsonObject = myClient.get(urls.用户信息url(), cookie);
            String userName = jsonObject.getString("userName");
            String userId = jsonObject.getString("userId");
            User user = userDao.get(userName);
            if (user != null) {
                return MyJson.error(MyError.用户已存在);
            } else {
                User user1 = new User();
                user1.setUserid(userId);
                user1.setUsername(userName);
                user1.setCookie(cookie);
                userDao.add(user1);
                return MyJson.success(user1);
            }

        } catch (ForestNetworkException ex) {
            return MyJson.error(MyError.cookie无效);
        }
    }

    public MyJson set(String username, String cookie) {
        try {
            //获取用户当前公会
            JSONObject jsonObject = myClient.get(urls.用户信息url(), cookie);
            String demo = jsonObject.getString("userName");
            if (username.equals(demo)) {
                userDao.setcookie(username, cookie);
                return MyJson.success();
            } else {
                return MyJson.error(MyError.cookie与用户不匹配);
            }

        } catch (ForestNetworkException ex) {
            return MyJson.error(MyError.cookie无效);
        }
    }

    public MyJson check(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userName = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    userName = cookie.getValue();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username", userName);
                    return MyJson.success(jsonObject);
                }
            }
        }
        return MyJson.error();
    }

    public MyJson logout(String username, HttpServletResponse response) {
        Cookie cookie = new Cookie("username", null);
        cookie.setMaxAge(0);
        cookie.setValue(null);
        response.addCookie(cookie);
        return MyJson.success();
    }

    public MyJson login(HttpServletResponse response, String username) {
        User user = userDao.get(username);
        if (user != null) {
            Cookie cookie = new Cookie("username", user.getUsername());
            cookie.setMaxAge(60 * 60 * 24 * 365);
            cookie.setPath("/");
            response.addCookie(cookie);
            return MyJson.success();
        } else {
            return MyJson.error(MyError.不存在该用户);
        }
    }

}
