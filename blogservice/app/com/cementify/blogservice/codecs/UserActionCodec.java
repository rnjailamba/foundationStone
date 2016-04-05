package com.cementify.blogservice.codecs;


import com.cementify.blogservice.models.UserAction;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by roshan on 05/04/16.
 */
public class UserActionCodec implements CollectibleCodec<UserAction>,GenericCodec {

    private CodecRegistry codecRegistry;

    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public UserActionCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    public UserAction generateIdIfAbsentFromDocument(UserAction document) {
        if (!documentHasId(document)) {
            document.generateId();
        }
        return document;
    }

    public boolean documentHasId(UserAction document) {
        if (document.getUserActionId() == null) {
            return false;
        }
        return true;
    }

    public BsonValue getDocumentId(UserAction document) {
        if (!documentHasId(document)) {
            throw new IllegalStateException("Document does not have id");
        }
        return new BsonString(document.getUserActionId().toHexString());
    }


    @Override
    public void encode(BsonWriter writer,UserAction value, EncoderContext encoderContext) {
        writeDocument(writer,value,encoderContext);
    }


    @Override
    public UserAction decode(BsonReader reader, DecoderContext decoderContext) {
        UserAction userAction =new UserAction();
        userAction = (UserAction) readDocument(reader, decoderContext, userAction);
        return userAction;
    }

    @Override
    public Class<UserAction> getEncoderClass() {
        return UserAction.class;
    }
}
