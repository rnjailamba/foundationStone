package com.cementify.blogservice.models.request;

import org.bson.types.ObjectId;
import play.data.validation.Constraints;

/**
 * Created by roshan on 04/04/16.
 */
public class DeleteCommentRequest {

    @Constraints.Required
    private ObjectId blogId;

    @Constraints.Required
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
