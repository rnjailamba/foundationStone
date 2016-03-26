package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.FieldName;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * Created by roshan on 26/03/16.
 */
public class VideoObject implements Bson{

    @FieldName(value = "video_url")
    private String videoUrl;

    @FieldName(value = "video_caption")
    private String videoCaption;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoCaption() {
        return videoCaption;
    }

    public void setVideoCaption(String videoCaption) {
        this.videoCaption = videoCaption;
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> aClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<VideoObject>(this, codecRegistry.get(VideoObject.class));
    }
}
