package com.mfzj.parttimer.bean;

import cn.bmob.v3.BmobObject;

public class ApplyTable extends BmobObject {
    private String apply;
    private User user;
    private JobSelection job;

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JobSelection getJob() {
        return job;
    }

    public void setJob(JobSelection job) {
        this.job = job;
    }
}
