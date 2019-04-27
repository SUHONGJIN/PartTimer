package com.mfzj.parttimer.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class JobSelection extends BmobObject {
    private String job_title;
    private String job_pay;
    private String job_address;
    private String job_company;
    private String job_phone;
    private String job_type;
    private String job_describe;
    private String job_people;
    private String job_time;
    private User boss;
    private BmobRelation collect;
    private BmobRelation apply;
    private String job_logo;

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_pay() {
        return job_pay;
    }

    public void setJob_pay(String job_pay) {
        this.job_pay = job_pay;
    }

    public String getJob_address() {
        return job_address;
    }

    public void setJob_address(String job_address) {
        this.job_address = job_address;
    }

    public String getJob_company() {
        return job_company;
    }

    public void setJob_company(String job_company) {
        this.job_company = job_company;
    }

    public String getJob_phone() {
        return job_phone;
    }

    public void setJob_phone(String job_phone) {
        this.job_phone = job_phone;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getJob_describe() {
        return job_describe;
    }

    public void setJob_describe(String job_describe) {
        this.job_describe = job_describe;
    }

    public String getJob_people() {
        return job_people;
    }

    public void setJob_people(String job_people) {
        this.job_people = job_people;
    }

    public String getJob_time() {
        return job_time;
    }

    public void setJob_time(String job_time) {
        this.job_time = job_time;
    }

    public User getBoss() {
        return boss;
    }

    public void setBoss(User boss) {
        this.boss = boss;
    }

    public BmobRelation getCollect() {
        return collect;
    }

    public void setCollect(BmobRelation collect) {
        this.collect = collect;
    }

    public BmobRelation getApply() {
        return apply;
    }

    public void setApply(BmobRelation apply) {
        this.apply = apply;
    }

    public String getJob_logo() {
        return job_logo;
    }

    public void setJob_logo(String job_logo) {
        this.job_logo = job_logo;
    }
}
