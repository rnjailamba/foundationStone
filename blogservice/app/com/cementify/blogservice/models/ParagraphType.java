package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.FieldName;

/**
 * Created by roshan on 12/03/16.
 */
public enum ParagraphType {
    Text("Text"),Image("Image"),Video("Video");

    @FieldName(value = "type")
    private String type;

    ParagraphType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
