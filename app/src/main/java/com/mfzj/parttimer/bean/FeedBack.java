package com.mfzj.parttimer.bean;

import cn.bmob.v3.BmobObject;

public class FeedBack extends BmobObject {
    private String feedbackType;
    private String feedbackContent;

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }
}
