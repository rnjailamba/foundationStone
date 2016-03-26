package com.cementify.blogservice.codecs;



import com.cementify.blogservice.models.Comment;
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
public class CommentCodec implements CollectibleCodec<Comment>,GenericCodec {

    private CodecRegistry codecRegistry;

    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public CommentCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    public Comment generateIdIfAbsentFromDocument(Comment document) {
        if (!documentHasId(document)) {
            document.generateId();
        }
        return document;
    }

    public boolean documentHasId(Comment document) {
        if (document.getCommentId() == null) {
            return false;
        }
        return true;
    }

    public BsonValue getDocumentId(Comment document) {
        if (!documentHasId(document)) {
            throw new IllegalStateException("Document does not have id");
        }
        return new BsonString(document.getCommentId().toHexString());
    }


    @Override
    public void encode(BsonWriter writer, Comment value, EncoderContext encoderContext) {
        writeDocument(writer,value,encoderContext);
    }


    @Override
    public Comment decode(BsonReader reader, DecoderContext decoderContext) {
        Comment comment=new Comment();
        comment = (Comment) readDocument(reader, decoderContext, comment);
        return comment;
    }

    @Override
    public Class<Comment> getEncoderClass() {
        return Comment.class;
    }
}
