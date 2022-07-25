package com.abocidee.servlet.tools;

import lombok.Data;

@Data

public class MyJson
{
    private static final int SUCCESS_CODE = 0;
    private static final int ERROR_CODE = 1;
    private static final String SUCCESS_RESULT = "操作成功！";
    private static final String ERROR_RESULT = "操作失败！";

    private int code;
    private String msg;
    private Object data;

    public MyJson() {}

    public static MyJson success()
    {
        MyJson MyJson = new MyJson();
        MyJson.setCode(0);
        MyJson.setMsg(SUCCESS_RESULT);
        return MyJson;
    }

    public static MyJson success(Object data)
    {
        MyJson MyJson = new MyJson();
        MyJson.setCode(0);
        MyJson.setMsg(SUCCESS_RESULT);
        MyJson.setData(data);
        return MyJson;
    }

    public static MyJson success(String msg)
    {
        MyJson MyJson = new MyJson();
        MyJson.setMsg(msg);
        return MyJson;
    }
    public static MyJson success(String msg,Object data)
    {
        MyJson MyJson = new MyJson();
        MyJson.setMsg(msg);
        MyJson.setData(data);
        return MyJson;
    }

    public static MyJson error(MyError myerror)
    {
        MyJson MyJson = new MyJson();
        MyJson.setCode(ERROR_CODE);
        MyJson.setMsg(myerror.getErrMsg());
        return MyJson;
    }

    public static MyJson error()
    {
        MyJson MyJson = new MyJson();
        MyJson.setCode(ERROR_CODE);
        return MyJson;
    }

}
