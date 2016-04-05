package com.cementify.blogservice.models.request;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by roshan on 05/04/16.
 */
public class LikeBlogRequest {

    private ObjectId blogId;

    private List<Integer> userIds;


    public ObjectId getBlogId() {
        return blogId;
    }

    public void setBlogId(ObjectId blogId) {
        this.blogId = blogId;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}
