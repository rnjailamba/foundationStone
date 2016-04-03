package com.cementify.blogservice.controllers;


import com.cementify.blogservice.models.Blog;
import com.cementify.blogservice.models.Comment;
import com.cementify.blogservice.models.CommentCollection;
import com.cementify.blogservice.models.mapping.BlogMapping;
import com.cementify.blogservice.models.request.BlogUpdateRequest;
import com.cementify.blogservice.models.request.CommentRequest;
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
            CompletionStage<?> completionStage=mongoHandler.updateManyDocuments(
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
        MongoHandler<Blog> blogMongoHandler=new MongoHandler<>();
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
                Logger.error("Exception raised during adding comment to blogId " + blog.getBlogId() + " is " + e);
                return null;
            }
        }).thenApply(blogCollectionForNoOfCollection -> {
            try {
                List<String> totalCommentFields = new ArrayList<>();
                totalCommentFields.add("total_comments");
                Bson commentsCollectionProjection = Projections.include(totalCommentFields);
                CommentCollection commentCollection1=new CommentCollection();
                commentCollection1.setBlogId(commentRequest.getBlogId());
                commentCollection1.setCollectionNo(((Blog) blogCollectionForNoOfCollection).getNoOfCommentsCollections());
                CompletionStage<?> commentCompletionStage = commentMongoHandler.readOneDocument(
                        commentCollection,commentCollection1);
                return ((CompletableFuture) commentCompletionStage).get();
            } catch (Exception e) {
                // Logger.error("Exception raised during adding comment to blogId " + ((Blog) blogCollectionForNoOfCollection).getBlogId() + " is " + e);
                return null;
            }
        }).thenApply(commentsCollectionForNoOfComment -> {
            if (((CommentCollection) commentsCollectionForNoOfComment) == null &&
                    ((CommentCollection) commentsCollectionForNoOfComment).getTotalComments() > 10) {
                return null;
            } else {
                Comment comment =commentRequest.getComment();
                comment.generateId();
                comment.setNoOfReplyCommentsCollections(0);
                Date date=new Date();
                comment.setPostedTime(date);
                CompletionStage<?> completionStage = commentMongoHandler.updateOneDocuments(commentCollection,
                        eq("_id", ((CommentCollection) commentsCollectionForNoOfComment).getCommentCollectionId()),
                        new Document("$push", new Document("comments",comment)).append("$inc",new Document("total_comments",1)));
                try {
                    return ((CompletableFuture) completionStage).get();
                } catch (Exception e) {
                    //Logger.error("Exception raised during adding comment to blogId " + blog.getBlogId() + " is " + e);
                    return null;
                }
            }
        }).thenApply(result->{
            return ok("hello");
        });
    }



    }




