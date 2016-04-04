package com.cementify.blogservice.models.request;

import org.bson.types.ObjectId;

/**
 * Created by roshan on 05/04/16.
 */
public class ReadCommentsRequest {

    private ObjectId blogId;

    private Integer collectionNo;

    private ObjectId parentId;

    public ObjectId getBlogId() {
        return blogId;
    }

    public void setBlogId(ObjectId blogId) {
        this.blogId = blogId;
    }

    public Integer getCollectionNo() {
        return collectionNo;
    }

    public void setCollectionNo(Integer collectionNo) {
        this.collectionNo = collectionNo;
    }

    public ObjectId getParentId() {
        return parentId;
    }

    public void setParentId(ObjectId parentId) {
        this.parentId = parentId;
    }
}
