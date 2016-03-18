package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.FieldName;
import org.bson.types.ObjectId;

/**
 * Created by roshan on 16/03/16.
 */
public class User {

    public User(){

    }

    public  User(ObjectId id,String name,String type){
        this.name=name;
        this.type=type;
        this.id=id;
    }


    @FieldName(value = "_id")
    private ObjectId id;

    public ObjectId getId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId generateId() {
        if (this.id == null) {
            id = new ObjectId();
        }
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @FieldName(value = "name")
    private String name;

    @FieldName(value = "type")
    private String type;


}
