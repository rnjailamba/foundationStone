package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.FieldName;
import com.cementify.blogservice.customannotations.Id;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by roshan on 25/03/16.
 */
public class Comment implements Bson{

    @Id(value = "comment_id")
    private ObjectId commentId;

    @FieldName(value = "posted_by")
    private PostedBy postedBy;

    @FieldName(value = "created_time")
    private Date createdDate;

    @FieldName(value = "modified_time")
    private Date  modifiedDate;

    @FieldName(value = "comment_content")
    private Paragraph commentContent;

    @FieldName(value = "likes")
    private List<Integer> likeUserList;

    @FieldName(value = "no_of_reply_comments_collection")
    private Integer noOfReplyCommentsCollections;

    @FieldName(value = "soft_delete")
    private Boolean  softDelete;

    public ObjectId generateId() {
        if (this.commentId == null) {
            commentId = new ObjectId();
        }
        return commentId;
    }


    public ObjectId getCommentId() {
        return commentId;
    }

    public void setCommentId(ObjectId commentId) {
        this.commentId = commentId;
    }

    public PostedBy getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(PostedBy postedBy) {
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

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> aClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<Comment>(this, codecRegistry.get(Comment.class));
    }



}
