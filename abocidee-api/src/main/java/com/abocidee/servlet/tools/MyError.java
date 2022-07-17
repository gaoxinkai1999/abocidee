package com.abocidee.servlet.tools;



public enum MyError
{
    用户cookie失效( "用户cookie失效"),
    公会cookie失效( "公会cookie失效"),
    超时( "超时"),
    不存在该用户( "不存在该用户"),
    cookie无效( "cookie无效"),
    当前公会未接入( "当前公会未接入"),
    未知错误( "未知错误"),
    用户已存在( "用户已存在"),
    公会已存在( "公会已存在"),
    cookie与用户不匹配( "cookie与用户不匹配"),
    cookie与公会不匹配( "cookie与公会不匹配"),
    不存在该题包( "不存在该题包"),;

    private String errMsg;
    public String getErrMsg()
    {
        return errMsg;
    }

    public void setErrMsg(String errMsg)
    {
        this.errMsg = errMsg;
    }

    MyError(String errMsg)
    {
        this.errMsg = errMsg;
    }
}
