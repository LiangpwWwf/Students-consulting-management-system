package com.example.hin.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by WWF on 2017/3/18.
 */

public class Feedback extends BmobObject {

    String uId;
    String content;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
