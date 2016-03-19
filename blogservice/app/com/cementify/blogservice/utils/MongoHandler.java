package com.cementify.blogservice.utils;

import com.cementify.blogservice.models.User;
import com.google.inject.Singleton;
import com.mongodb.Block;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoCollection;

import org.bson.conversions.Bson;
import play.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import static com.mongodb.client.model.Filters.*;

/**
 * Created by roshan on 19/03/16.
 */
public class MongoHandler<T> {

    public CompletionStage<?> readOneDocument(MongoCollection<T> collection,Bson filters){
        CompletionStage<T> completionStage=new CompletableFuture<>();
        if(filters==null)
            collection.find().first(getSingleFindResultCallBack(completionStage));
        else
            collection.find(filters).first(getSingleFindResultCallBack(completionStage));
        return completionStage;
    }


    public SingleResultCallback<T> getSingleFindResultCallBack(CompletionStage<T> completionStage){
        SingleResultCallback<T> singleResultCallback=new SingleResultCallback<T>() {
            @Override
            public void onResult(T o, Throwable throwable) {
                if(throwable!=null){
                    Logger.error("Object not fetched due to error " +throwable.getMessage());
                    ((CompletableFuture)completionStage).completeExceptionally(throwable);
                }else {
                    Logger.info("Object fetched is " + o);
                    ((CompletableFuture) completionStage).complete(o);
                }

            }
        };
                return singleResultCallback;
    }

    public CompletionStage<?> readDocuments(MongoCollection<T> collection,Bson filters){
        CompletionStage<T> completionStage=new CompletableFuture<>();
        List<T> list=new ArrayList<>();
        if(filters==null)
            collection.find().forEach(processEachObject(list),getCallbackWhenFindManyFinished(completionStage,list));
        else
            collection.find(filters).forEach(processEachObject(list),getCallbackWhenFindManyFinished(completionStage,list));
        return completionStage;
    }

    public Block<T> processEachObject(List<T> list) {
        Block<T>  printDocumentBlock = new Block<T>() {
            @Override
            public void apply(final T object) {
                Logger.info("Object fetched is " + object);
                list.add(object);
            }
        };
        return printDocumentBlock;
    }

    public SingleResultCallback<Void> getCallbackWhenFindManyFinished(CompletionStage<T> completionStage,List<T> list){
        SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
            @Override
            public void onResult(final Void result, final Throwable throwable) {
                if(throwable!=null){
                    Logger.error("Object not inserted due to error " +throwable.getMessage());
                    ((CompletableFuture)completionStage).completeExceptionally(throwable);
                }else{
                    Logger.info("List of Object is sucessfully fetched");
                    ((CompletableFuture)completionStage).complete(list);
                }
            }
        };
        return callbackWhenFinished;
    }


    public CompletionStage<?> insertOneDocuments(MongoCollection<T> collection,T object){
        CompletionStage<T> completionStage=new CompletableFuture<>();
        collection.insertOne(object, getSingleInsertResultCallBack(completionStage, object));
        return completionStage;
    }

    public SingleResultCallback<Void> getSingleInsertResultCallBack(CompletionStage<T> completionStage,T object){
        SingleResultCallback<Void> singleResultCallback=new SingleResultCallback<Void>() {
            @Override
            public void onResult(Void result, Throwable throwable) {
                   if(throwable!=null){
                    Logger.error("Object not inserted due to error " +throwable.getMessage());
                    ((CompletableFuture)completionStage).completeExceptionally(throwable);
                   }else{
                       Logger.info("Object inserted is "+object);
                       ((CompletableFuture)completionStage).complete(object);
                   }
            }
        };
        return singleResultCallback;
    }




}
