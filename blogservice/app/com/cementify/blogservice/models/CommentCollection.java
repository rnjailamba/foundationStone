package com.cementify.blogservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by roshan on 12/03/16.
 */
public class CommentCollection {

    @JsonProperty("_id")
    private String commentCollectionId;

    @JsonProperty("parent_id")
    private String parentId;

    @JsonProperty("blog_id")
    private String blogId;

    @JsonProperty("no_of_comments")
    private Integer noOfComments;

}
