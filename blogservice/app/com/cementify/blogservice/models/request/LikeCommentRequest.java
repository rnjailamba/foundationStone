package com.cementify.blogservice.models.request;

import org.bson.types.ObjectId;
import play.data.validation.Constraints;

/**
 * Created by roshan on 04/04/16.
 */
public class LikeCommentRequest extends LikeBlogRequest {

    @Constraints.Required
    private ObjectId commentId;

    public ObjectId getCommentId() {
        return commentId;
    }

    public void setCommentId(ObjectId commentId) {
        this.commentId = commentId;
    }
}
