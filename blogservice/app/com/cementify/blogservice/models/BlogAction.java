package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.FieldName;
import com.cementify.blogservice.customannotations.Id;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by roshan on 05/04/16.
 */
public class BlogAction  implements Bson {

    @Id(value = "blog_id")
    private ObjectId blogId;

    @FieldName(value = "modified_time")
    private Date  modifiedDate;

    public ObjectId getBlogId() {
        return blogId;
    }

    public void setBlogId(ObjectId blogId) {
        this.blogId = blogId;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> aClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<BlogAction>(this, codecRegistry.get(BlogAction.class));
    }
}
