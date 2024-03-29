package com.cementify.blogservice.models.request;


import com.cementify.blogservice.models.Paragraph;
import org.bson.types.ObjectId;
import play.data.validation.Constraints;

import java.util.Date;
import java.util.List;

/**
 * Created by roshan on 06/04/16.
 */
public class CreateBlogRequest {

    @Constraints.Required
    private Integer postedBy;


    @Constraints.Required
    private String categoryId;

    private String subCategoryId;

    @Constraints.Required
    private String title;

    private List<String> tags;

    @Constraints.Required
    private String blogType;

    private Integer approvedBy;

    private Integer noOfView;


    private List<Integer> likeUserList;

    @Constraints.Required
    private List<Paragraph> paragraphs;

    private String userAboutus;

    private Integer totalNoOfComment;



    private String coverImage;

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;



    public Integer getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Integer postedBy) {
        this.postedBy = postedBy;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUserAboutus() {
        return userAboutus;
    }

    public void setUserAboutus(String userAboutus) {
        this.userAboutus = userAboutus;
    }

    public String getBlogType() {
        return blogType;
    }


    public void setBlogType(String blogType) {
        this.blogType = blogType;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Integer getTotalNoOfComment() {
        return totalNoOfComment;
    }

    public void setTotalNoOfComment(Integer totalNoOfComment) {
        this.totalNoOfComment = totalNoOfComment;
    }
}
