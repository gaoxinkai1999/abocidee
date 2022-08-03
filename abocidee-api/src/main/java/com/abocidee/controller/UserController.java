package com.abocidee.controller;

import com.abocidee.servlet.UserServlet;
import com.abocidee.servlet.tools.MyJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserServlet userServlet;

    @RequestMapping("/add")
    public MyJson login(String cookie) {
        return userServlet.add(cookie);
    }

    @RequestMapping("/set")
    public MyJson set(@CookieValue(value = "username",
            defaultValue = "null") String username, String cookie) {
        return userServlet.set(username, cookie);
    }

    @RequestMapping("/logout")
    public MyJson logout(@CookieValue(value = "username",
            defaultValue = "null") String username, HttpServletResponse response) {
        return userServlet.logout(response);
    }

    @RequestMapping("/check")
    public MyJson check(HttpServletRequest request) {
        return userServlet.check(request);
    }

    @RequestMapping("/login")
    public MyJson login(String username, HttpServletResponse response) {
        return userServlet.login(response, username);
    }

}
