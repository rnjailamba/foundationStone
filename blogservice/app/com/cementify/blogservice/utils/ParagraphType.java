package com.cementify.blogservice.utils;

/**
 * Created by roshan on 12/03/16.
 */
public enum ParagraphType {
    Text("Text"),Image("Image"),Video("Video");

    private String type;

    ParagraphType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
