package com.cementify.blogservice.models.request;

import com.cementify.blogservice.models.Comment;
import org.bson.types.ObjectId;
import play.data.validation.Constraints;

/**
 * Created by roshan on 04/04/16.
 */
public class UpdateCommentRequest {

    @Constraints.Required
    private ObjectId blogId;

    @Constraints.Required
    private Comment comment;

    public ObjectId getBlogId() {
        return blogId;
    }

    public void setBlogId(ObjectId blogId) {
        this.blogId = blogId;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
