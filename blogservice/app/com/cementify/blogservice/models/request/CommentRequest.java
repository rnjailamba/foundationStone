package com.cementify.blogservice.models.request;

import com.cementify.blogservice.models.Comment;
import org.bson.types.ObjectId;
import play.data.validation.Constraints;

/**
 * Created by roshan on 28/03/16.
 */
public class CommentRequest {


    private ObjectId parentId;

    @Constraints.Required
    private ObjectId blogId;

    @Constraints.Required
    private Comment comment;


    public ObjectId getParentId() {
        return parentId;
    }

    public void setParentId(ObjectId parentId) {
        this.parentId = parentId;
    }

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
