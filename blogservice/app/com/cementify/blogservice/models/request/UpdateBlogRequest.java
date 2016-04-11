package com.cementify.blogservice.models.request;

import com.cementify.blogservice.models.Blog;
import play.data.validation.Constraints;

/**
 * Created by roshan on 27/03/16.
 */
public class UpdateBlogRequest {

    @Constraints.Required
    private Blog oldBlogCondition;

    @Constraints.Required
    private Blog newBlogData;

    private String userAboutus;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Blog getOldBlogCondition() {
        return oldBlogCondition;
    }

    public void setOldBlogCondition(Blog oldBlogCondition) {
        this.oldBlogCondition = oldBlogCondition;
    }

    public Blog getNewBlogData() {
        return newBlogData;
    }

    public void setNewBlogData(Blog newBlogData) {
        this.newBlogData = newBlogData;
    }


    public String getUserAboutus() {
        return userAboutus;
    }

    public void setUserAboutus(String userAboutus) {
        this.userAboutus = userAboutus;
    }
}
