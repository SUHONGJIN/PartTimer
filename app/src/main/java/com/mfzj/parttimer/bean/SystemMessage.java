package com.mfzj.parttimer.bean;

import cn.bmob.v3.BmobObject;

public class SystemMessage extends BmobObject {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
