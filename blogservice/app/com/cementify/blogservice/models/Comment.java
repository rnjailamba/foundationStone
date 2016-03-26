package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.FieldName;
import com.cementify.blogservice.customannotations.Id;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by roshan on 25/03/16.
 */
public class Comment implements Bson{

    @Id(value = "_id")
    private ObjectId commentId;

    @FieldName(value = "posted_by")
    private PostedBy postedBy;

    @FieldName(value = "time")
    private DateTime dateTime;

    @FieldName(value = "comment_content")
    private Paragraph commentContent;

    @FieldName(value = "likes")
    private List<Integer> likeUserList;

    @FieldName(value = "no_of_reply_comments_collection")
    private Integer noOfReplyCommentsCollections;


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

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
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

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> aClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<Comment>(this, codecRegistry.get(Comment.class));
    }



}
