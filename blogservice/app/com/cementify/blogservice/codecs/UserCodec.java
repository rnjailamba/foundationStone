package com.cementify.blogservice.codecs;

import com.cementify.blogservice.utils.FieldData;
import com.cementify.blogservice.models.User;
import com.cementify.blogservice.utils.InvokeGetterSetter;
import com.cementify.blogservice.utils.ObjectAndDocumentFieldNameMappping;
import org.bson.*;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by roshan on 16/03/16.
 */
public class UserCodec implements CollectibleCodec<User>,GenericCodec {

    public UserCodec() {

    }

    private CodecRegistry codecRegistry;

    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public UserCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    public User generateIdIfAbsentFromDocument(User document) {
        if (!documentHasId(document)) {
            document.generateId();
        }
        return document;
    }

    public boolean documentHasId(User document) {
        if (document.getId() == null) {
            return false;
        }
        return true;
    }

    public BsonValue getDocumentId(User document) {
        if (!documentHasId(document)) {
            throw new IllegalStateException("Document does not have id");
        }
        return new BsonString(document.getId().toHexString());
    }


    @Override
    public void encode(BsonWriter writer, User value, EncoderContext encoderContext) {
        writeDocument(writer,value,encoderContext);
    }


    @Override
    public User decode(BsonReader reader, DecoderContext decoderContext) {
        User user = new User();
        user = (User) readDocument(reader, decoderContext, user);
        return user;
    }

    @Override
    public Class<User> getEncoderClass() {
        return User.class;
    }
}
