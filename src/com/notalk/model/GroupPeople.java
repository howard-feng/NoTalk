package com.notalk.model;

/*
*  联系人类
* */
public class GroupPeople {
    private String nickname;
    private String sid;
//    private

    public GroupPeople(String nickname, String sid){

    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return this.nickname;
    }

    public String getSid() {
        return sid;
    }
}