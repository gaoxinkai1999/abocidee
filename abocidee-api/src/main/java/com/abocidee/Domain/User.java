package com.abocidee.Domain;

public class User {
    private String username;
    private String cookie;
    private String userid;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", cookie='" + cookie + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
