package com.cementify.blogservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.conversions.Bson;

import java.util.Date;
import java.util.List;

/**
 * Created by roshan on 12/03/16.
 */
public class Blog  {

    @JsonProperty("_id")
    private String blogId;

    @JsonProperty("posted_by")
    private Integer postedBy;

    @JsonProperty("created_time")
    private Date createdDate;

    @JsonProperty("modified_time")
    private Date  modifiedDate;

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("no_of_view")
    private Integer noOfView;

    @JsonProperty("likes")
    private List<Integer> likeUserList;

    @JsonProperty("paragraphs")
    private List<Paragraph> paragraphs;

    @JsonProperty("is_verified")
    private Boolean isverified;

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public Integer getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Integer postedBy) {
        this.postedBy = postedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Integer getNoOfView() {
        return noOfView;
    }

    public void setNoOfView(Integer noOfView) {
        this.noOfView = noOfView;
    }

    public List<Integer> getLikeUserList() {
        return likeUserList;
    }

    public void setLikeUserList(List<Integer> likeUserList) {
        this.likeUserList = likeUserList;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public Boolean getIsverified() {
        return isverified;
    }

    public void setIsverified(Boolean isverified) {
        this.isverified = isverified;
    }

    public Integer getNoOfCommentsCollections() {
        return noOfCommentsCollections;
    }

    public void setNoOfCommentsCollections(Integer noOfCommentsCollections) {
        this.noOfCommentsCollections = noOfCommentsCollections;
    }

    @JsonProperty("no_of_comments_collection")
    private Integer noOfCommentsCollections;



}
