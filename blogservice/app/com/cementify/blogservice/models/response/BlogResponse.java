package com.cementify.blogservice.models.response;

import com.cementify.blogservice.models.Paragraph;
import play.data.validation.Constraints;

import java.util.Date;
import java.util.List;

/**
 * Created by roshan on 26/03/16.
 */
public class BlogResponse {

    private String blogId;

    private Integer customerId;


    private String birthday;

    private String createdDate;

    private String categoryId;

    private Integer age;

    private String aboutUser;

    private String profilePic;

    private Boolean isMale;

    private List<String> tags;


    private Integer noOfView;

    private String name;


    private List<Integer> likeUserList;


    private List<Paragraph> paragraphs;


    private Boolean isVerified;

    private String subCategoryId;

    private String title;


    private Integer noOfCommentsCollections;

    private String blogType;

    private Integer approvedBy;

    private Integer totalNoOfComment;

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


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(Boolean isMale) {
        this.isMale = isMale;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
