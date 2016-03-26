package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.FieldName;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * Created by roshan on 26/03/16.
 */
public class ImageObject implements Bson{

    @FieldName(value = "image_url")
    private String imageUrl;

    @FieldName(value = "image_caption")
    private String imageCaption;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }


    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> aClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<ImageObject>(this, codecRegistry.get(ImageObject.class));
    }
}
