package com.cementify.blogservice.models.request;

import com.cementify.blogservice.models.Blog;

/**
 * Created by roshan on 27/03/16.
 */
public class BlogUpdateRequest {

    private Blog oldBlogCondition;

    private Blog newBlogData;

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
}
