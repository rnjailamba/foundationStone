package com.cementify.blogservice.models;

import com.cementify.blogservice.utils.ParagraphType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by roshan on 12/03/16.
 */
public class Paragraph {

    @JsonProperty("img_urls")
    private List<String> ImgUrls;

    @JsonProperty("video_urls")
    private List<String> videoUrls;

    public List<String> getImgUrls() {
        return ImgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        ImgUrls = imgUrls;
    }

    public List<String> getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(List<String> videoUrls) {
        this.videoUrls = videoUrls;
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

    @JsonProperty("text")
    private String text;

    @JsonProperty("type")
    private ParagraphType paragraphType;
}
