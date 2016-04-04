package com.cementify.blogservice.models.request;

import org.bson.types.ObjectId;

/**
 * Created by roshan on 04/04/16.
 */
public class DeleteCommentRequest {

    private ObjectId blogId;

    private ObjectId commentId;

    public ObjectId getBlogId() {
            return blogId;
        }

    public void setBlogId(ObjectId blogId) {
            this.blogId = blogId;
        }


    public ObjectId getCommentId() {
        return commentId;
    }

    public void setCommentId(ObjectId commentId) {
        this.commentId = commentId;
    }

}
