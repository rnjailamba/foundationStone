package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.FieldName;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * Created by roshan on 25/03/16.
 */

public class PostedBy implements Bson{

    @FieldName(value = "user_name")
    private String userName;

    @FieldName(value = "user_id")
    private Integer userId;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> aClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<PostedBy>(this, codecRegistry.get(PostedBy.class));
    }
}
