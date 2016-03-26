package com.cementify.blogservice.codecs;



import com.cementify.blogservice.models.CommentCollection;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;


/**
 * Created by roshan on 16/03/16.
 */
public class CommentCollectionCodec implements CollectibleCodec<CommentCollection>,GenericCodec {

    private CodecRegistry codecRegistry;

    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public CommentCollectionCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    public CommentCollection generateIdIfAbsentFromDocument(CommentCollection document) {
        if (!documentHasId(document)) {
            document.generateId();
        }
        return document;
    }

    public boolean documentHasId(CommentCollection document) {
        if (document.getCommentCollectionId() == null) {
            return false;
        }
        return true;
    }

    public BsonValue getDocumentId(CommentCollection document) {
        if (!documentHasId(document)) {
            throw new IllegalStateException("Document does not have id");
        }
        return new BsonString(document.getCommentCollectionId().toHexString());
    }


    @Override
    public void encode(BsonWriter writer, CommentCollection value, EncoderContext encoderContext) {
        writeDocument(writer,value,encoderContext);
    }


    @Override
    public CommentCollection decode(BsonReader reader, DecoderContext decoderContext) {
        CommentCollection commentCollection=new CommentCollection();
        commentCollection = (CommentCollection) readDocument(reader, decoderContext, commentCollection);
        return commentCollection;
    }

    @Override
    public Class<CommentCollection> getEncoderClass() {
        return CommentCollection.class;
    }
}
