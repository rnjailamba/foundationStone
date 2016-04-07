package com.cementify.blogservice.models.mapping;

import com.cementify.blogservice.models.Comment;
import com.cementify.blogservice.models.CommentCollection;
import com.cementify.blogservice.models.response.CommentCollectionResponse;
import com.cementify.blogservice.models.response.CommentResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roshan on 08/04/16.
 */
public class CommentCollectionMapping {
    public static CommentCollectionResponse getCommentResponseFromCommentCollection(CommentCollection commentCollection){
        if(commentCollection == null)
            return null;
        CommentCollectionResponse commentCollectionResponse =new CommentCollectionResponse();
        return getCommentResponseFromCommentCollection(commentCollection,commentCollectionResponse);
    }

    public static CommentCollectionResponse getCommentResponseFromCommentCollection(CommentCollection commentCollection,CommentCollectionResponse commentCollectionResponse){
        commentCollectionResponse.setCollectionNo(commentCollection.getCollectionNo());
        commentCollectionResponse.setCommentCollectionId(commentCollection.getCommentCollectionId().toString());
        commentCollectionResponse.setParentId(commentCollection.getParentId().toString());
        commentCollectionResponse.setTotalComments(commentCollection.getTotalComments());
        commentCollectionResponse.setBlogId(commentCollection.getBlogId().toString());
        List<CommentResponse> commentResponseList =new ArrayList<>();
        if(commentCollection.getComments() !=null){
            for(Comment comment :commentCollection.getComments()){
                CommentResponse commentResponse = new CommentResponse();
                if(comment.getSoftDelete()){
                    commentResponse.setCommentContent(null);
                }else {
                    commentResponse.setCommentContent(comment.getCommentContent());
                }
                commentResponse.setCommentId(comment.getCommentId().toString());
                commentResponse.setLikeUserList(comment.getLikeUserList());
                commentResponse.setPostedBy(comment.getPostedBy());
                commentResponse.setModifiedDate(comment.getModifiedDate());
                commentResponse.setNoOfReplyCommentsCollections(comment.getNoOfReplyCommentsCollections());
                commentResponse.setSoftDelete(comment.getSoftDelete());
                commentResponseList.add(commentResponse);
            }
        }
        commentCollectionResponse.setComments(commentResponseList);
        return commentCollectionResponse;
    }
}
