package com.cementify.blogservice.controllers;

import com.cementify.blogservice.models.User;
import com.cementify.blogservice.utils.MongoClientInstance;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;
import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import static com.mongodb.client.model.Filters.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

/**
 * Created by roshan on 11/03/16.
 */
public class BlogController extends Controller {

    @Inject
    MongoClientInstance mongoClientInstance;

    public Result ping() {
        return ok("Hello BlogService");
    }

    /*@BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> createBlog(){
        JsonNode jsonNode=request().body().asJson();
        if(jsonNode.get("posted_by")==null){

        }
    }
    */
    public CompletionStage<Result> test(){
        MongoCollection<User> collection=
                mongoClientInstance.getMongoClient().getDatabase("testdb").getCollection("col",User.class);
        User user =new User();
        user.setName("test2");
        CompletionStage<User> completionStage=new CompletableFuture();
        SingleResultCallback<User> printDocument = new SingleResultCallback<User>() {
            @Override
            public void onResult(final User document, final Throwable t) {
                System.out.println(Json.toJson(document));

            }
        };
        //collection.find(eq("name", user.getName())).first(printDocument);
        User user1=new User();
        user1.setName("ttthfh");
        user1.setType("ggjjkjjk9jj");
        collection.insertOne(user1, new SingleResultCallback<Void>() {
            @Override
            public void onResult(Void aVoid, Throwable throwable) {
                System.out.println("inserted");
                ((CompletableFuture)completionStage).complete(user1);
            }
        });
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




}
