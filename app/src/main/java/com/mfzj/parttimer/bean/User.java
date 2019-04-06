package com.mfzj.parttimer.bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    /**
     * 昵称
     */
    private String nick;
    /**
     * 座右铭
     */
    private String motto;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 头像
     */
    private String avatar;
    //姓名
    private String name;
    //身份
    private String identity;
    //出生
    private String birth;
    //手机号码
    private String phone;
    //简介
    private String intro;
    //工作经验
    private String experience;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
