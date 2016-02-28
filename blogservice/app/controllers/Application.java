package controllers;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public Result index() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("col");
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));
        collection.insertOne(doc, new SingleResultCallback<Void>() {
            @Override
            public void onResult(final Void result, final Throwable t) {

            }
        });
        SingleResultCallback<Document> printDocument = new SingleResultCallback<Document>() {
            @Override
            public void onResult(final Document document, final Throwable t) {
                System.out.println(document.toJson());
            }
        };
        return ok("hello");
    }

}
