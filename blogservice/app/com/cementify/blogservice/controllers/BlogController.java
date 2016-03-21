package com.cementify.blogservice.controllers;

import com.cementify.blogservice.models.Address;
import com.cementify.blogservice.models.User;
import com.cementify.blogservice.utils.MongoClientInstance;
import com.cementify.blogservice.utils.MongoHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;
import java.util.List;
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
        List<User> users=new ArrayList<User>();
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

        MongoHandler<User> mongoHandler=new MongoHandler<>();
        //insert one document  CompletionStage<?> completionStage=mongoHandler.insertOneDocuments(collection,user);
        // inser multiple documents CompletionStage<?> completionStage=mongoHandler.insertManyDocuments(collection,users);
        //read multiple documents  CompletionStage<?> completionStage=mongoHandler.readOneDocument(collection,eq("name","testing1"));
       // read single documents CompletionStage<?> completionStage=mongoHandler.readOneDocument(collection, eq("name", "testing"));
        /*return CompletableFuture.supplyAsync(() ->{
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
        CompletionStage<?> completionStage=mongoHandler.deleteManyDocuments(collection, eq("name", "ttthfh"));
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
    }



}
