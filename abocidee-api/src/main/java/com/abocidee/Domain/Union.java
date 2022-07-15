package com.abocidee.Domain;

public class Union {
    private String unionname;
    private String cookie;
    private String unionid;
    private String token;

    @Override
    public String toString() {
        return unionname;

    }

    public String getUnionname() {
        return unionname;
    }

    public void setUnionname(String unionname) {
        this.unionname = unionname;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
