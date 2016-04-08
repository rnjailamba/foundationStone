package com.cementify.blogservice.models.request;

import org.bson.types.ObjectId;
import play.data.validation.Constraints;

import java.util.List;

/**
 * Created by roshan on 05/04/16.
 */
public class UserActionRequest {

    @Constraints.Required
    private Integer userId;

    @Constraints.Required
    private List<ObjectId> blogIds;

    @Constraints.Required
    private String action;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<ObjectId> getBlogIds() {
        return blogIds;
    }

    public void setBlogIds(List<ObjectId> blogIds) {
        this.blogIds = blogIds;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
