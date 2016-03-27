package com.cementify.blogservice.controllers;


import com.cementify.blogservice.models.Blog;
import com.cementify.blogservice.models.mapping.BlogMapping;
import com.cementify.blogservice.models.response.BlogResponse;
import com.cementify.blogservice.utils.MongoClientInstance;
import com.cementify.blogservice.utils.MongoHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.mongodb.async.client.MongoCollection;
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
                    return null;
                }

            }
            else
                return status(400, "Bad request");
        }).thenApply(json -> {
            if (json == null)
                return status(400, "Bad request");
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




