package com.abocidee.controller;

import com.abocidee.servlet.QrcodeService;
import com.abocidee.servlet.tools.MyJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class QrcodeComtroller {
    @Autowired
    private QrcodeService qrcodeService;
    @RequestMapping("/getimg")
    public MyJson getimg() {
        return qrcodeService.getqrcode();
    }
    @RequestMapping("/checkstate")
    public MyJson checkstate(String sign,String time){
        return qrcodeService.checkstate(sign,time);
    }
}
