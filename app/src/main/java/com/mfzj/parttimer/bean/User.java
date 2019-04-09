package com.mfzj.parttimer.bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    //昵称
    private String nick;
    //座右铭
    private String motto;
    //年龄
    private Integer age;
    //性别
    private String gender;
    //城市
    private String city;
    //实名认证状态
    private String isverify;
    //头像
    private String avatar;
    //姓名
    private String name;
    //身份
    private String identity;
    //出生
    private String birth;
    //手机号码
    private String phone;
    //邮箱
    private String myemail;
    //简介
    private String intro;
    //工作经验
    private String experience;
    //身份证姓名
    private String id_card_name;
    //身份证号码
    private String id_card_number;
    //身份证正面
    private String id_card_pic_z;
    //身份证反面
    private String id_card_pic_f;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsverify() {
        return isverify;
    }

    public void setIsverify(String isverify) {
        this.isverify = isverify;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMyemail() {
        return myemail;
    }

    public void setMyemail(String myemail) {
        this.myemail = myemail;
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

    public String getId_card_name() {
        return id_card_name;
    }

    public void setId_card_name(String id_card_name) {
        this.id_card_name = id_card_name;
    }

    public String getId_card_number() {
        return id_card_number;
    }

    public void setId_card_number(String id_card_number) {
        this.id_card_number = id_card_number;
    }

    public String getId_card_pic_z() {
        return id_card_pic_z;
    }

    public void setId_card_pic_z(String id_card_pic_z) {
        this.id_card_pic_z = id_card_pic_z;
    }

    public String getId_card_pic_f() {
        return id_card_pic_f;
    }

    public void setId_card_pic_f(String id_card_pic_f) {
        this.id_card_pic_f = id_card_pic_f;
    }
}
