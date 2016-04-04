package com.cementify.blogservice.models.request;

import com.cementify.blogservice.models.Comment;
import org.bson.types.ObjectId;

/**
 * Created by roshan on 04/04/16.
 */
public class UpdateCommentRequest {

    private ObjectId blogId;

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
