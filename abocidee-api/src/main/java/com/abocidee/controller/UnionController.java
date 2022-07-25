package com.abocidee.controller;

import com.abocidee.servlet.UnionServlet;
import com.abocidee.servlet.tools.MyJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/union")
@Slf4j
@CrossOrigin
public class UnionController {
    @Autowired
    private UnionServlet unionServlet;

    @RequestMapping("/add")
    public MyJson add(String unionid, String cookie) {
        return unionServlet.add(unionid, cookie);
    }

    @RequestMapping("/set")
    public MyJson set(String unionname, String cookie) {
        return unionServlet.set(unionname, cookie);
    }

    @RequestMapping("/getAll")
    public MyJson getAll() {
        return unionServlet.getAll();
    }

}
