package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.EnclosedGenericClass;
import com.cementify.blogservice.customannotations.FieldName;
import com.cementify.blogservice.customannotations.Id;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import play.data.validation.Constraints;

import java.util.Date;
import java.util.List;

/**
 * Created by roshan on 12/03/16.
 */
public class Blog implements Bson {

    @Id(value = "_id")
    private ObjectId blogId;

    @FieldName(value = "posted_by")
    private Integer postedBy;

    @FieldName(value = "created_time")
    private Date createdDate;

    @FieldName(value = "modified_time")
    private Date  modifiedDate;

    @FieldName(value = "category_id")
    private String categoryId;

    @FieldName(value = "subCategory_id")
    private String subCategoryId;

    @FieldName(value = "title")
    private String title;

    @FieldName(value = "tags")
    private List<String> tags;

    @FieldName(value = "no_of_view")
    private Integer noOfView;

    @FieldName(value = "likes")
    private List<Integer> likeUserList;

    @EnclosedGenericClass(value = Paragraph.class)
    @FieldName(value = "paragraphs")
    private List<Paragraph> paragraphs;

    @FieldName(value = "is_verified")
    private Boolean isVerified;

    @FieldName(value = "no_of_comments_collection")
    private Integer noOfCommentsCollections;

    @FieldName(value = "blog_type")
    private String blogType;

    @FieldName(value = "approved_by")
    private Integer approvedBy;

    @FieldName(value = "total_no_of_comment")
    private Integer totalNoOfComment;

    @FieldName(value = "cover_image_url")
    private String coverImageUrl;

    public ObjectId generateId() {
        if (this.blogId == null) {
            blogId = new ObjectId();
        }
        return blogId;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public ObjectId getBlogId() {
        return blogId;
    }

    public void setBlogId(ObjectId blogId) {
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

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isverified) {
        this.isVerified = isverified;
    }

    public Integer getNoOfCommentsCollections() {
        return noOfCommentsCollections;
    }

    public void setNoOfCommentsCollections(Integer noOfCommentsCollections) {
        this.noOfCommentsCollections = noOfCommentsCollections;
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

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> aClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<Blog>(this, codecRegistry.get(Blog.class));
    }


}
