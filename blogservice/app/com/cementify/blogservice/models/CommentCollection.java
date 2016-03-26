package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.EnclosedGenericClass;
import com.cementify.blogservice.customannotations.FieldName;
import com.cementify.blogservice.customannotations.Id;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by roshan on 12/03/16.
 */
public class CommentCollection implements Bson{

    @Id(value = "_id")
    private ObjectId commentCollectionId;

    @FieldName(value = "parent_id")
    private String parentId;

    @FieldName(value = "blog_id")
    private String blogId;

    @FieldName(value = "no_of_comments")
    private Integer noOfComments;

    @EnclosedGenericClass(value = Comment.class)
    @FieldName(value = "comments")
    List<Comment> comments;

    public ObjectId generateId() {
        if (this.commentCollectionId == null) {
            commentCollectionId = new ObjectId();
        }
        return commentCollectionId;
    }

    public ObjectId getCommentCollectionId() {
        return commentCollectionId;
    }

    public void setCommentCollectionId(ObjectId commentCollectionId) {
        this.commentCollectionId = commentCollectionId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public Integer getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(Integer noOfComments) {
        this.noOfComments = noOfComments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> aClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<CommentCollection>(this, codecRegistry.get(CommentCollection.class));
    }

}
