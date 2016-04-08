package com.cementify.blogservice.models.request;

import org.bson.types.ObjectId;
import play.data.validation.Constraints;

import java.util.List;

/**
 * Created by roshan on 05/04/16.
 */
public class LikeBlogRequest {

    @Constraints.Required
    private ObjectId blogId;

    @Constraints.Required
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
