package com.cementify.blogservice.models.request;

import org.bson.types.ObjectId;

/**
 * Created by roshan on 04/04/16.
 */
public class LikeBlogRequest {

    private ObjectId blogId;

    private Integer userId;

    public ObjectId getBlogId() {
        return blogId;
    }

    public void setBlogId(ObjectId blogId) {
        this.blogId = blogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
