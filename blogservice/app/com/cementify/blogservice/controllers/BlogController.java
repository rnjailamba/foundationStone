package com.cementify.blogservice.controllers;


import com.cementify.blogservice.models.Blog;
import com.cementify.blogservice.models.Comment;
import com.cementify.blogservice.models.CommentCollection;
import com.cementify.blogservice.models.mapping.BlogMapping;
import com.cementify.blogservice.models.request.*;
import com.cementify.blogservice.models.response.BlogResponse;
import com.cementify.blogservice.utils.MongoClientInstance;
import com.cementify.blogservice.utils.MongoHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import play.Configuration;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;


import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

/**
 * Created by roshan on 11/03/16.
 */
public class BlogController extends Controller {

    @Inject
    MongoClientInstance mongoClientInstance;

    @Inject
    FormFactory formFactory;

    @Inject
    WSClient ws;

    @Inject
    Configuration configuration;

    public Result ping() {
        return ok("Hello BlogService");
    }


    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> createBlog(){
        Form<Blog> blogForm = formFactory.form(Blog.class);
        blogForm=blogForm.bindFromRequest();
        if (blogForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> status(400, "Bad request"));
        }
        Blog blog=blogForm.get();
        MongoCollection<Blog> collection=
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").getCollection("blog_collection",Blog.class);
        MongoHandler<Blog> mongoHandler=new MongoHandler<>();
        return CompletableFuture.supplyAsync(() ->{
            Date date=new Date();
            blog.setModifiedDate(date);
            blog.setCreatedDate(date);
            if(blog.getNoOfCommentsCollections() == null)
                 blog.setNoOfCommentsCollections(0);
            CompletionStage<?> completionStage=mongoHandler.insertOneDocuments(collection,blog);
            try{
                return ((CompletableFuture)completionStage).get();
            }catch (Exception e){
                Logger.error("Exception raised during storing Blogs to MongoDb "+e);
                return null;
            }
        }).thenApply(blogs -> {
            if(blogs!=null)
                return status(200, "Post Successfully Created");
            else
                return status(400, "Bad request");
        });

    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> readBlogs(){
        Form<Blog> blogForm = formFactory.form(Blog.class);
        blogForm=blogForm.bindFromRequest();
        if (blogForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> status(400, "Bad request"));
        }
        Blog blog=blogForm.get();
        MongoCollection<Blog> collection=
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").getCollection("blog_collection",Blog.class);
        MongoHandler<Blog> mongoHandler=new MongoHandler<>();
        Map<Integer,Integer> customerIdPositionMapping = new HashMap<Integer, Integer>();
        List<BlogResponse> blogResponses= new ArrayList<BlogResponse>();
        return CompletableFuture.supplyAsync(() -> {
            CompletionStage<?> completionStage = mongoHandler.readDocuments(collection, blog);
            try {
                return ((CompletableFuture) completionStage).get();
            } catch (Exception e) {
                Logger.error("Exception raised during Fetching Blogs from MongoDb "+e);
                return null;
            }
        }).thenApply(blogs -> {
            if(blogs!=null) {
                List<Integer> customerIds = new ArrayList<Integer>();
                String userServiceUrl=configuration.getString("userServiceUrl");
                int i=0;
                for(Blog blog1 :(List<Blog>)blogs){
                    blogResponses.add(BlogMapping.getBlogResponseFromBlog(blog1));
                    customerIdPositionMapping.put(blog1.getPostedBy(),i);
                    customerIds.add(blog1.getPostedBy());
                    i++;
                }
                JsonNode postData = Json.newObject().set("customerIds",Json.toJson(customerIds));
                CompletionStage<WSResponse> responsePromise = ws.url(userServiceUrl + "findCustomerByIds").
                        setHeader("Content-Type", "application/json").post(postData);
                try {
                    return ((CompletableFuture)responsePromise.thenApply(WSResponse::asJson)).get();
                } catch (Exception e) {
                    Logger.error("Exception raised during fetching "+customerIds+" from userService "+e);
                    return null;
                }

            }
            else
                return status(400, "Bad request");
        }).thenApply(json -> {
            if (json == null) {
                Logger.error("Null response during fetching from userService");
                return status(400, "Bad request");
            }
            for (int j = 0; j < ((JsonNode) json).size(); j++) {
                JsonNode jsonNode1 = ((JsonNode) json).get(j);
                Integer customerId = jsonNode1.get("customerId").asInt();
                Integer position = customerIdPositionMapping.get(customerId);
                BlogResponse blogResponse = blogResponses.get(position);
                if (jsonNode1.get("email") != null)
                    blogResponse.setEmail(jsonNode1.get("email").asText());
                if (jsonNode1.get("birthday") != null)
                    blogResponse.setBirthday(jsonNode1.get("birthday").asText());
            }
            return ok(Json.toJson(blogResponses));
        });

    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> updateBlog(){
        Form<BlogUpdateRequest> blogForm = formFactory.form(BlogUpdateRequest.class);
        blogForm=blogForm.bindFromRequest();
        if (blogForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> status(400, "Bad request"));
        }
        BlogUpdateRequest blogUpdateRequest=blogForm.get();
        MongoCollection<Blog> collection=
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").getCollection("blog_collection",Blog.class);
        MongoHandler<Blog> mongoHandler=new MongoHandler<>();
        return CompletableFuture.supplyAsync(() ->{
            Blog oldBlogCondition =blogUpdateRequest.getOldBlogCondition();
            Blog newBlogData=blogUpdateRequest.getNewBlogData();
            Date date=new Date();
            newBlogData.setModifiedDate(date);
            CompletionStage<?> completionStage=mongoHandler.updateOneDocuments(
                    collection,oldBlogCondition,new Document("$set", newBlogData));
            try{
                return ((CompletableFuture)completionStage).get();
            }catch (Exception e){
                Logger.error("Exception raised during updating Blogs to MongoDb "+e);
                return null;
            }
        }).thenApply(blogs -> {
            if(blogs!=null)
                return status(200, "Post Successfully updated");
            else
                return status(400, "Bad request");
        });

    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> updateBlogs(){
        Form<BlogUpdateRequest> blogForm = formFactory.form(BlogUpdateRequest.class);
        blogForm=blogForm.bindFromRequest();
        if (blogForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> status(400, "Bad request"));
        }
        BlogUpdateRequest blogUpdateRequest=blogForm.get();
        MongoCollection<Blog> collection=
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").getCollection("blog_collection",Blog.class);
        MongoHandler<Blog> mongoHandler=new MongoHandler<>();
        return CompletableFuture.supplyAsync(() ->{
            Blog oldBlogCondition =blogUpdateRequest.getOldBlogCondition();
            Blog newBlogData=blogUpdateRequest.getNewBlogData();
            Date date=new Date();
            newBlogData.setModifiedDate(date);
            CompletionStage<?> completionStage = mongoHandler.updateManyDocuments(
                    collection, oldBlogCondition, new Document("$set", newBlogData));
            try{
                return ((CompletableFuture)completionStage).get();
            }catch (Exception e){
                Logger.error("Exception raised during updating Blogs to MongoDb "+e);
                return null;
            }
        }).thenApply(blogs -> {
            if(blogs!=null)
                return status(200, "Post Successfully updated");
            else
                return status(400, "Bad request");
        });

    }



    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> addComment(){
        Form<CommentRequest> commentForm = formFactory.form(CommentRequest.class);
        commentForm = commentForm.bindFromRequest();
        if (commentForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> status(400, "Bad request"));
        }
        CommentRequest commentRequest = commentForm.get();
        MongoCollection<CommentCollection> commentCollection=
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").
                        getCollection("comment_collection", CommentCollection.class);
        MongoHandler<CommentCollection> commentMongoHandler=new MongoHandler<>();
        MongoCollection<Blog> blogCollection =
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").
                        getCollection("blog_collection", Blog.class);
        MongoHandler<Blog> blogMongoHandler = new MongoHandler<>();
        List<String> fields = new ArrayList<>();
        fields.add("no_of_comments_collection");
        Bson projection = Projections.include(fields);
        Blog blog =new Blog();
        blog.setBlogId(commentRequest.getBlogId());
        return CompletableFuture.supplyAsync(() -> {
                CompletionStage<?> blogCompletionStage = blogMongoHandler.findOneDocumentWithProjection(
                        blogCollection, blog, projection);
                try {
                    return ((CompletableFuture) blogCompletionStage).get();
                } catch (Exception e) {
                    Logger.error("Exception raised during adding comment to blogId " + commentRequest.getBlogId() + " is " + e);
                    return null;
                }
        }).thenApply(blogCollectionForNoOfCollection -> {
            try {
                List<String> totalCommentFields = new ArrayList<>();
                totalCommentFields.add("total_comments");
                totalCommentFields.add("collection_no");
                Bson commentsCollectionProjection = Projections.include(totalCommentFields);
                int collectionNo = ((Blog) blogCollectionForNoOfCollection).getNoOfCommentsCollections();
                CommentCollection commentCollectionMongoRequest = new CommentCollection();
                commentCollectionMongoRequest.setBlogId(commentRequest.getBlogId());
                commentCollectionMongoRequest.setCollectionNo(collectionNo);
                commentCollectionMongoRequest.setParentId(commentRequest.getBlogId());
                CompletionStage<?> commentCompletionStage = commentMongoHandler.findOneDocumentWithProjection(
                        commentCollection, commentCollectionMongoRequest, commentsCollectionProjection);
                return ((CompletableFuture) commentCompletionStage).get();
            } catch (Exception e) {
                Logger.error("Exception raised during adding comment to blogId " + ((Blog) blogCollectionForNoOfCollection).getBlogId() + " is " + e);
                return null;
            }
        }).thenApply(commentsCollectionForNoOfComment -> {
            CommentCollection commentCollectionResponse = ((CommentCollection) commentsCollectionForNoOfComment);
            Comment comment =commentRequest.getComment();
            comment.generateId();
            comment.setNoOfReplyCommentsCollections(0);
            Date date=new Date();
            comment.setCreatedDate(date);
            comment.setModifiedDate(date);
            if (commentCollectionResponse == null ||
                    commentCollectionResponse.getTotalComments() > 10) {
                CommentCollection newCommentCollection = new CommentCollection();
                newCommentCollection.setTotalComments(1);
                newCommentCollection.setComments(Arrays.asList(comment));
                newCommentCollection.setBlogId(commentRequest.getBlogId());
                newCommentCollection.setParentId(commentRequest.getBlogId());
                newCommentCollection.setCreatedDate(date);
                newCommentCollection.setModifiedDate(date);
                if(commentCollectionResponse == null){
                    newCommentCollection.setCollectionNo(1);
                }else{
                    newCommentCollection.setCollectionNo(commentCollectionResponse.getCollectionNo()+1);
                }
                CompletionStage<?> commentCompletionStage = commentMongoHandler.insertOneDocuments(
                        commentCollection, newCommentCollection);
                CompletionStage<?> blogCompletionStage =blogMongoHandler.updateOneDocuments(blogCollection, and(eq("_id", commentRequest.getBlogId()),
                                eq("comments.comment_id", commentRequest.getParentId())),
                        new Document("$inc", new Document("no_of_comments_collection", 1)));
                try{
                    return CompletableFuture.allOf((CompletableFuture)commentCompletionStage,(CompletableFuture)blogCompletionStage);
                } catch (Exception e) {
                    Logger.error("Exception raised during adding comment to blogId " + blog.getBlogId() + " is " + e);
                    return null;
                }
            } else {
                CompletionStage<?> completionStage = commentMongoHandler.updateOneDocuments(commentCollection,
                        eq("_id", commentCollectionResponse.getCommentCollectionId()),
                        new Document("$push", new Document("comments", comment)).append("$inc",new Document("total_comments",1)));
                try {
                    return ((CompletableFuture) completionStage).get();
                } catch (Exception e) {
                    Logger.error("Exception raised during adding comment to blogId " + blog.getBlogId() + " is " + e);
                    return null;
                }
            }
        }).thenApply(result -> {
            return status(200, "Comment Added Sucessfully");
        });
    }


    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> addReplyComment() {
        Form<CommentRequest> commentForm = formFactory.form(CommentRequest.class);
        commentForm = commentForm.bindFromRequest();
        if (commentForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> status(400, "Bad request"));
        }
        CommentRequest commentRequest = commentForm.get();
        MongoCollection<CommentCollection> commentCollection =
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").
                        getCollection("comment_collection", CommentCollection.class);
        MongoHandler<CommentCollection> commentMongoHandler = new MongoHandler<>();
        List<String> fields = new ArrayList<>();
        fields.add("no_of_reply_comments_collection");
        Bson projection = Projections.include(fields);
        return CompletableFuture.supplyAsync(() ->{
            CompletionStage<?> commentCompletionStage =commentMongoHandler.findOneDocumentWithProjection(commentCollection,and(eq("blog_id",commentRequest.getBlogId()),
                            eq("comments.comment_id",commentRequest.getParentId())),and(Projections.elemMatch("comments"),projection));
            try {
                return ((CompletableFuture) commentCompletionStage).get();
            } catch (Exception e) {
                Logger.error("Exception raised during adding comment to blogId " + commentRequest.getBlogId() + " is " + e);
                return null;
            }
        }).thenApply( response ->{
            CommentCollection commentCollectionResponse =(CommentCollection)response;
            Comment comment = commentCollectionResponse.getComments().get(0);
            try {
                List<String> totalCommentFields = new ArrayList<>();
                totalCommentFields.add("total_comments");
                totalCommentFields.add("collection_no");
                Bson commentsCollectionProjection = Projections.include(totalCommentFields);
                int collectionNo = comment.getNoOfReplyCommentsCollections();
                CommentCollection commentCollectionMongoRequest = new CommentCollection();
                commentCollectionMongoRequest.setBlogId(commentRequest.getBlogId());
                commentCollectionMongoRequest.setCollectionNo(collectionNo);
                commentCollectionMongoRequest.setParentId(commentRequest.getParentId());
                CompletionStage<?> commentCompletionStage = commentMongoHandler.findOneDocumentWithProjection(
                        commentCollection, commentCollectionMongoRequest, commentsCollectionProjection);
                return ((CompletableFuture) commentCompletionStage).get();
            } catch (Exception e) {
                Logger.error("Exception raised during adding comment to blogId " + commentCollectionResponse.getBlogId() + " is " + e);
                return null;
            }
        }).thenApply(commentsCollectionForNoOfComment -> {
            CommentCollection commentCollectionResponse = ((CommentCollection) commentsCollectionForNoOfComment);
            Comment comment = commentRequest.getComment();
            comment.generateId();
            comment.setNoOfReplyCommentsCollections(0);
            Date date = new Date();
            comment.setModifiedDate(date);
            comment.setCreatedDate(date);
            if (commentCollectionResponse == null ||
                    commentCollectionResponse.getTotalComments() > 10) {
                CommentCollection newCommentCollection = new CommentCollection();
                newCommentCollection.setTotalComments(1);
                newCommentCollection.setComments(Arrays.asList(comment));
                newCommentCollection.setBlogId(commentRequest.getBlogId());
                newCommentCollection.setParentId(commentRequest.getParentId());
                newCommentCollection.setCreatedDate(date);
                newCommentCollection.setModifiedDate(date);
                if (commentCollectionResponse == null) {
                    newCommentCollection.setCollectionNo(1);
                } else {
                    newCommentCollection.setCollectionNo(commentCollectionResponse.getCollectionNo() + 1);
                }
                CompletionStage<?> commentCollectionCreateCompletionStage = commentMongoHandler.insertOneDocuments(
                        commentCollection, newCommentCollection);
                CompletionStage<?> commentCollectionParentUpdateCompletionStage = commentMongoHandler.updateOneDocuments(commentCollection,
                        and(eq("blog_id", commentRequest.getBlogId()), eq("comments.comment_id", commentRequest.getParentId())),
                        new Document("$inc", new Document("comments.$.no_of_reply_comments_collection", 1)));
                try {
                    return CompletableFuture.allOf((CompletableFuture) commentCollectionCreateCompletionStage, (CompletableFuture) commentCollectionParentUpdateCompletionStage);
                } catch (Exception e) {
                    Logger.error("Exception raised during adding comment to blogId " + commentRequest.getBlogId() + " is " + e);
                    return null;
                }
            } else {
                CompletionStage<?> completionStage = commentMongoHandler.updateOneDocuments(commentCollection,
                        eq("_id", commentCollectionResponse.getCommentCollectionId()),
                        new Document("$push", new Document("comments", comment)).append("$inc", new Document("total_comments", 1)));
                try {
                    return ((CompletableFuture) completionStage).get();
                } catch (Exception e) {
                    Logger.error("Exception raised during adding comment to blogId " + commentRequest.getBlogId() + " is " + e);
                    return null;
                }
            }
        }).thenApply(result -> {
            return status(200, "Comment Added Sucessfully");
        });

    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> likeBlog() {
        Form<LikeBlogRequest> likeBlogRequestForm = formFactory.form(LikeBlogRequest.class);
        likeBlogRequestForm = likeBlogRequestForm.bindFromRequest();
        if (likeBlogRequestForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> status(400, "Bad request"));
        }
        LikeBlogRequest likeBlogRequest = likeBlogRequestForm.get();
        MongoHandler<Blog> blogMongoHandler = new MongoHandler<>();
        MongoCollection<Blog> blogCollection =
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").
                        getCollection("blog_collection", Blog.class);
        return CompletableFuture.supplyAsync(() ->{
            CompletionStage<?> completionStage = blogMongoHandler.updateOneDocuments(blogCollection,
                    eq("_id", likeBlogRequest.getBlogId()),
                    new Document("$push", new Document("likes", likeBlogRequest.getUserId())));
            try {
                return ((CompletableFuture) completionStage).get();
            } catch (Exception e) {
                Logger.error("Exception raised during adding comment to blogId " + likeBlogRequest.getBlogId() + " is " + e);
                return null;
            }
            }).thenApply(result -> {
            return status(200,"Liked Blog");
        });

    }


    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> unlikeBlog() {
        Form<LikeBlogRequest> likeBlogRequestForm = formFactory.form(LikeBlogRequest.class);
        likeBlogRequestForm = likeBlogRequestForm.bindFromRequest();
        if (likeBlogRequestForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> status(400, "Bad request"));
        }
        LikeBlogRequest likeBlogRequest = likeBlogRequestForm.get();
        MongoHandler<Blog> blogMongoHandler = new MongoHandler<>();
        MongoCollection<Blog> blogCollection =
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").
                        getCollection("blog_collection", Blog.class);
        return CompletableFuture.supplyAsync(() ->{
            CompletionStage<?> completionStage = blogMongoHandler.updateOneDocuments(blogCollection,
                    eq("_id", likeBlogRequest.getBlogId()),
                    new Document("$pull", new Document("likes", likeBlogRequest.getUserId())));
            try {
                return ((CompletableFuture) completionStage).get();
            } catch (Exception e) {
                Logger.error("Exception raised during adding comment to blogId " + likeBlogRequest.getBlogId() + " is " + e);
                return null;
            }
        }).thenApply(result -> {
            return status(200,"UnLiked Blog");
        });

    }


    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> likeComment() {
        Form<LikeCommentRequest> likeCommentRequestForm = formFactory.form(LikeCommentRequest.class);
        likeCommentRequestForm = likeCommentRequestForm.bindFromRequest();
        if (likeCommentRequestForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> status(400, "Bad request"));
        }
        LikeCommentRequest likeCommentRequest = likeCommentRequestForm.get();
        MongoCollection<CommentCollection> commentCollection =
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").
                        getCollection("comment_collection", CommentCollection.class);
        MongoHandler<CommentCollection> commentMongoHandler = new MongoHandler<>();
        return CompletableFuture.supplyAsync(() ->{
            CompletionStage<?> completionStage = commentMongoHandler.updateOneDocuments(commentCollection,
                    and(eq("blog_id", likeCommentRequest.getBlogId()), eq("comments.comment_id", likeCommentRequest.getCommentId())),
                    new Document("$push", new Document("comments.$.likes", likeCommentRequest.getUserId())));
            try {
                return ((CompletableFuture) completionStage).get();
            } catch (Exception e) {
                Logger.error("Exception raised during adding comment to blogId " + likeCommentRequest.getBlogId() + " is " + e);
                return null;
            }
        }).thenApply(result -> {
            return status(200,"Liked Comment");
        });

    }


    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> unlikeComment() {
        Form<LikeCommentRequest> likeCommentRequestForm = formFactory.form(LikeCommentRequest.class);
        likeCommentRequestForm = likeCommentRequestForm.bindFromRequest();
        if (likeCommentRequestForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> status(400, "Bad request"));
        }
        LikeCommentRequest likeCommentRequest = likeCommentRequestForm.get();
        MongoCollection<CommentCollection> commentCollection =
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").
                        getCollection("comment_collection", CommentCollection.class);
        MongoHandler<CommentCollection> commentMongoHandler = new MongoHandler<>();
        return CompletableFuture.supplyAsync(() ->{
            CompletionStage<?> completionStage = commentMongoHandler.updateOneDocuments(commentCollection,
                    and(eq("blog_id", likeCommentRequest.getBlogId()), eq("comments.comment_id", likeCommentRequest.getCommentId())),
                    new Document("$pull", new Document("comments.$.likes", likeCommentRequest.getUserId())));
            try {
                return ((CompletableFuture) completionStage).get();
            } catch (Exception e) {
                Logger.error("Exception raised during adding comment to blogId " + likeCommentRequest.getBlogId() + " is " + e);
                return null;
            }
        }).thenApply(result -> {
            return status(200,"Unliked Comment");
        });

    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> updateComment(){
        Form<UpdateCommentRequest> updateCommentForm = formFactory.form(UpdateCommentRequest.class);
        updateCommentForm = updateCommentForm.bindFromRequest();
        if (updateCommentForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> status(400, "Bad request"));
        }
        UpdateCommentRequest updateCommentRequest = updateCommentForm.get();
        MongoCollection<CommentCollection> commentCollection =
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").
                        getCollection("comment_collection", CommentCollection.class);
        MongoHandler<CommentCollection> commentMongoHandler = new MongoHandler<>();
        return CompletableFuture.supplyAsync(() ->{
            Comment updateComment =updateCommentRequest.getComment();
            Date date=new Date();
            updateComment.setModifiedDate(date);
            CompletionStage<?> completionStage = commentMongoHandler.updateManyDocuments(
                    commentCollection,and(eq("blog_id", updateCommentRequest.getBlogId()),
                            eq("comments.comment_id", updateCommentRequest.getComment().getCommentId())),
                            new Document("$set", new Document("comments.$",updateComment)));
            try{
                return ((CompletableFuture)completionStage).get();
            }catch (Exception e){
                Logger.error("Exception raised during updating Comment to MongoDb "+e);
                return null;
            }
        }).thenApply(comments -> {
            if(comments!=null)
                return status(200, "Comment Successfully updated");
            else
                return status(400, "Bad request");
        });

    }


    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> deleteComment(){
        Form<DeleteCommentRequest> deleteCommentForm = formFactory.form(DeleteCommentRequest.class);
        deleteCommentForm  = deleteCommentForm.bindFromRequest();
        if (deleteCommentForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> status(400, "Bad request"));
        }
        DeleteCommentRequest deleteCommentRequest = deleteCommentForm.get();
        MongoCollection<CommentCollection> commentCollection =
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").
                        getCollection("comment_collection", CommentCollection.class);
        MongoHandler<CommentCollection> commentMongoHandler = new MongoHandler<>();
        return CompletableFuture.supplyAsync(() ->{
            Comment deleteComment =new Comment();
            Date date=new Date();
            deleteComment.setCommentId(deleteCommentRequest.getCommentId());
            deleteComment.setModifiedDate(date);
            deleteComment.setSoftDelete(true);
            CompletionStage<?> completionStage = commentMongoHandler.updateManyDocuments(
                    commentCollection,and(eq("blog_id", deleteCommentRequest.getBlogId()),
                            eq("comments.comment_id", deleteCommentRequest.getCommentId())),
                    new Document("$set", new Document("comments.$",deleteComment)));
            try{
                return ((CompletableFuture)completionStage).get();
            }catch (Exception e){
                Logger.error("Exception raised during updating Comment to MongoDb "+e);
                return null;
            }
        }).thenApply(comments -> {
            if(comments!=null)
                return status(200, "Comment Deleted Successfully ");
            else
                return status(400, "Bad request");
        });

    }


    



    }




