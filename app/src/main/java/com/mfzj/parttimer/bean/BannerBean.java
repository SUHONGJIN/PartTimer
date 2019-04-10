package com.mfzj.parttimer.bean;

import cn.bmob.v3.BmobObject;

public class BannerBean extends BmobObject {
    private String banner_title;
    private String banner_image_url;
    private String banner_web_url;

    public String getBanner_title() {
        return banner_title;
    }

    public void setBanner_title(String banner_title) {
        this.banner_title = banner_title;
    }

    public String getBanner_image_url() {
        return banner_image_url;
    }

    public void setBanner_image_url(String banner_image_url) {
        this.banner_image_url = banner_image_url;
    }

    public String getBanner_web_url() {
        return banner_web_url;
    }

    public void setBanner_web_url(String banner_web_url) {
        this.banner_web_url = banner_web_url;
    }
}
