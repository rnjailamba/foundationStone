package com.cementify.blogservice.controllers;


import com.cementify.blogservice.models.Blog;
import com.cementify.blogservice.models.mapping.BlogMapping;
import com.cementify.blogservice.models.response.BlogResponse;
import com.cementify.blogservice.utils.MongoClientInstance;
import com.cementify.blogservice.utils.MongoHandler;
import com.google.inject.Inject;
import com.mongodb.async.client.MongoCollection;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by roshan on 11/03/16.
 */
public class BlogController extends Controller {

    @Inject
    MongoClientInstance mongoClientInstance;

    @Inject
    FormFactory formFactory;


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
        return CompletableFuture.supplyAsync(() ->{
            CompletionStage<?> completionStage=mongoHandler.readDocuments(collection, blog);
            try{
                return ((CompletableFuture)completionStage).get();
            }catch (Exception e){
                return null;
            }
        }).thenApply(blogs -> {
            if(blogs!=null) {
                List<BlogResponse> blogResponses= new ArrayList<BlogResponse>();
                for(Blog blog1 :(List<Blog>)blogs){
                    blogResponses.add(BlogMapping.getBlogResponseFromBlog(blog1));
                }
                return ok(Json.toJson(blogResponses));
            }
            else
                return status(400, "Bad request");
        });

    }




/*
    public CompletionStage<Result> test(){
        MongoCollection<Blog> collection=
                mongoClientInstance.getMongoClient().getDatabase("blog_post_database").getCollection("blog_collection",Blog.class);
        /*List<User> users=new ArrayList<User>();
        User user=null;
        for(int i=1;i<=3;i++){
            user =new User();
            user.setName("testing"+i);
            user.setType("tyuueueu");
            List<String> list=new ArrayList<>();
            list.add("first");
            list.add("second");
            list.add("third");
            user.setListCheck(list);
            List<List<Address>> addressesList=new ArrayList<>();
            for(int j=0;j<3;j++){
                List<Address> addresses=new ArrayList<>();
                Address address=new Address();
                address.setIsMale(false);
                address.setPhoneNo("90176728838");
                addresses.add(address);
                addresses.add(address);
                addressesList.add(addresses);
            }

            user.setAddress(addressesList);
            users.add(user);
        }
*/
    /*
        MongoHandler<Blog> mongoHandler=new MongoHandler<>();
        //insert one document  CompletionStage<?> completionStage=mongoHandler.insertOneDocuments(collection,user);
        // inser multiple documents CompletionStage<?> completionStage=mongoHandler.insertManyDocuments(collection,users);
        //read multiple documents  CompletionStage<?> completionStage=mongoHandler.readOneDocument(collection,eq("name","testing1"));
       // read single documents
        /*User user1=new User();
        user1.setName("test");
        CompletionStage<?> completionStage=mongoHandler.readDocuments(collection, user1);
        */
    /*
        return CompletableFuture.supplyAsync(() ->{
            try{
                return ((CompletableFuture)completionStage).get();
            }catch (Exception e){
                return null;
            }
        }).thenApply(usr -> {
            if(usr!=null)
                return ok(Json.toJson(usr));
            else
                return ok("raj");
        });
    }
*/

        //count no of document CompletionStage<?> completionStage=mongoHandler.countDocuments(collection,eq("name","testh"));
        /*return CompletableFuture.supplyAsync(() ->{
                 try{
                     return ((CompletableFuture)completionStage).get();
                 }catch (Exception e){
                     return null;
                 }
     }).thenApply(count -> {
         if(count!=null)
             return ok(Json.toJson(count));
         else
             return ok("raj");
     });
    }
*/
        /*CompletionStage<?> completionStage=mongoHandler.deleteManyDocuments(collection, eq("name", "ttthfh"));
        return CompletableFuture.supplyAsync(() ->{
            try{
                return ((CompletableFuture)completionStage).get();
            }catch (Exception e){
                return null;
            }
        }).thenApply(result -> {
            if(result!=null)
                return ok(Json.toJson(((DeleteResult)result).getDeletedCount()));
            else
                return ok("raj");
        });
        */

    }




