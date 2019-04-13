package com.mfzj.parttimer.bean;

import cn.bmob.v3.BmobObject;

public class StrategyTable extends BmobObject {
    private String strategy_title;
    private Integer strategy_like;
    private Integer strategy_read;
    private String strategy_image_url;
    private String strategy_web_url;

    public String getStrategy_title() {
        return strategy_title;
    }

    public void setStrategy_title(String strategy_title) {
        this.strategy_title = strategy_title;
    }

    public Integer getStrategy_like() {
        return strategy_like;
    }

    public void setStrategy_like(Integer strategy_like) {
        this.strategy_like = strategy_like;
    }

    public Integer getStrategy_read() {
        return strategy_read;
    }

    public void setStrategy_read(Integer strategy_read) {
        this.strategy_read = strategy_read;
    }

    public String getStrategy_image_url() {
        return strategy_image_url;
    }

    public void setStrategy_image_url(String strategy_image_url) {
        this.strategy_image_url = strategy_image_url;
    }

    public String getStrategy_web_url() {
        return strategy_web_url;
    }

    public void setStrategy_web_url(String strategy_web_url) {
        this.strategy_web_url = strategy_web_url;
    }
}
