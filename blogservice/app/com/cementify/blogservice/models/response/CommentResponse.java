package com.cementify.blogservice.models.response;

import com.cementify.blogservice.models.Paragraph;
import com.cementify.blogservice.models.PostedBy;

import java.util.Date;
import java.util.List;

/**
 * Created by roshan on 08/04/16.
 */
public class CommentResponse {

    private String commentId;

    private PostedBy postedBy;

    private Date  modifiedDate;

    private Paragraph commentContent;

    private List<Integer> likeUserList;

    private Integer noOfReplyCommentsCollections;

    private Boolean  softDelete;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public PostedBy getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(PostedBy postedBy) {
        this.postedBy = postedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Paragraph getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(Paragraph commentContent) {
        this.commentContent = commentContent;
    }

    public List<Integer> getLikeUserList() {
        return likeUserList;
    }

    public void setLikeUserList(List<Integer> likeUserList) {
        this.likeUserList = likeUserList;
    }

    public Integer getNoOfReplyCommentsCollections() {
        return noOfReplyCommentsCollections;
    }

    public void setNoOfReplyCommentsCollections(Integer noOfReplyCommentsCollections) {
        this.noOfReplyCommentsCollections = noOfReplyCommentsCollections;
    }

    public Boolean getSoftDelete() {
        return softDelete;
    }

    public void setSoftDelete(Boolean softDelete) {
        this.softDelete = softDelete;
    }
}
