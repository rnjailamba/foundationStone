package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.EnclosedGenericClass;
import com.cementify.blogservice.customannotations.FieldName;
import com.cementify.blogservice.customannotations.Id;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

/**
 * Created by roshan on 12/03/16.
 */
public class CommentCollection implements Bson{

    @Id(value = "_id")
    private ObjectId commentCollectionId;

    @FieldName(value = "parent_id")
    private ObjectId parentId;

    @FieldName(value = "blog_id")
    private ObjectId blogId;

    @FieldName(value = "total_comments")
    private Integer totalComments;

    @EnclosedGenericClass(value = Comment.class)
    @FieldName(value = "comments")
    List<Comment> comments;

    @FieldName(value = "collection_no")
    private Integer collectionNo;

    @FieldName(value = "created_time")
    private Date createdDate;

    @FieldName(value = "modified_time")
    private Date  modifiedDate;

    public ObjectId generateId() {
        if (this.commentCollectionId == null) {
            commentCollectionId = new ObjectId();
        }
        return commentCollectionId;
    }

    public Integer getCollectionNo() {
        return collectionNo;
    }

    public void setCollectionNo(Integer collectionNo) {
        this.collectionNo = collectionNo;
    }

    public ObjectId getCommentCollectionId() {
        return commentCollectionId;
    }

    public void setCommentCollectionId(ObjectId commentCollectionId) {
        this.commentCollectionId = commentCollectionId;
    }

    public ObjectId getParentId() {
        return parentId;
    }

    public void setParentId(ObjectId parentId) {
        this.parentId = parentId;
    }

    public ObjectId getBlogId() {
        return blogId;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }

    public void setBlogId(ObjectId blogId) {
        this.blogId = blogId;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> aClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<CommentCollection>(this, codecRegistry.get(CommentCollection.class));
    }

}
