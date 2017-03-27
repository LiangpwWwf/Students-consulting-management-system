package com.example.hin.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by WWF on 2017/3/25.
 */

public class Favourite extends BmobObject {

    private String userId;
    private String postId;
    private Post post;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
