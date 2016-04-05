package com.cementify.blogservice.models;

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
 * Created by roshan on 05/04/16.
 */
public class UserAction implements Bson {

    @Id(value = "_id")
    private ObjectId userActionId;

    @FieldName(value = "created_time")
    private Date createdDate;

    @FieldName(value = "modified_time")
    private Date  modifiedDate;

    @FieldName(value = "keyword")
    private List<String> keyword;

    @FieldName(value = "blog_likes")
    private List<BlogAction> blogLikes;

    @FieldName(value = "bookmarks")
    private List<BlogAction> bookMarks;

    public ObjectId getUserActionId() {
        return userActionId;
    }

    public void setUserActionId(ObjectId userActionId) {
        this.userActionId = userActionId;
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

    public List<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<String> keyword) {
        this.keyword = keyword;
    }

    public List<BlogAction> getBlogLikes() {
        return blogLikes;
    }

    public void setBlogLikes(List<BlogAction> blogLikes) {
        this.blogLikes = blogLikes;
    }

    public List<BlogAction> getBookMarks() {
        return bookMarks;
    }

    public void setBookMarks(List<BlogAction> bookMarks) {
        this.bookMarks = bookMarks;
    }

    public ObjectId generateId() {
        if (this.userActionId == null) {
            userActionId = new ObjectId();
        }
        return userActionId;
    }
    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> aClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<UserAction>(this, codecRegistry.get(UserAction.class));
    }
}
