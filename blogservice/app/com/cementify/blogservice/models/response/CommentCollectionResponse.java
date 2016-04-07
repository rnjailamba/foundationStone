package com.cementify.blogservice.models.response;

import java.util.List;

/**
 * Created by roshan on 08/04/16.
 */
public class CommentCollectionResponse {

    private String commentCollectionId;

    private String parentId;

    private String blogId;

    private Integer totalComments;


    List<CommentResponse> comments;

    private Integer collectionNo;

    public String getCommentCollectionId() {
        return commentCollectionId;
    }

    public void setCommentCollectionId(String commentCollectionId) {
        this.commentCollectionId = commentCollectionId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }

    public Integer getCollectionNo() {
        return collectionNo;
    }

    public void setCollectionNo(Integer collectionNo) {
        this.collectionNo = collectionNo;
    }
}
