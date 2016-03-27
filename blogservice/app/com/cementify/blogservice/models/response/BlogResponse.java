package com.cementify.blogservice.models.response;

import com.cementify.blogservice.models.Paragraph;

import java.util.Date;
import java.util.List;

/**
 * Created by roshan on 26/03/16.
 */
public class BlogResponse {

    private String blogId;

    private Integer customerId;

    private String email;

    private String birthday;

    private String createdDate;

    private String categoryId;


    private List<String> tags;


    private Integer noOfView;


    private List<Integer> likeUserList;


    private List<Paragraph> paragraphs;


    private Boolean isVerified;


    private Integer noOfCommentsCollections;

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }


    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Integer getNoOfCommentsCollections() {
        return noOfCommentsCollections;
    }

    public void setNoOfCommentsCollections(Integer noOfCommentsCollections) {
        this.noOfCommentsCollections = noOfCommentsCollections;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}