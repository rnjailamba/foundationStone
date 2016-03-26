package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.EnclosedGenericClass;
import com.cementify.blogservice.customannotations.FieldName;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import java.util.List;

/**
 * Created by roshan on 12/03/16.
 */
public class Paragraph implements Bson{

    @EnclosedGenericClass(value = ImageObject.class)
    @FieldName(value = "image_list")
    private List<ImageObject> ImgageList;

    @EnclosedGenericClass(value = VideoObject.class)
    @FieldName(value = "video_list")
    private List<VideoObject> videoList;

    @FieldName(value = "text")
    private String text;

    @FieldName(value = "paragraph_type")
    private ParagraphType paragraphType;


    public List<VideoObject> getVideoList() {
        return videoList;
    }

    public List<ImageObject> getImgageList() {
        return ImgageList;
    }

    public void setImgageList(List<ImageObject> imgageList) {
        ImgageList = imgageList;
    }

    public void setVideoList(List<VideoObject> videoList) {
        this.videoList = videoList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ParagraphType getParagraphType() {
        return paragraphType;
    }

    public void setParagraphType(ParagraphType paragraphType) {
        this.paragraphType = paragraphType;
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> aClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<Paragraph>(this, codecRegistry.get(Paragraph.class));
    }
}
