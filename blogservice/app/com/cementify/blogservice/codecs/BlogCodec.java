package com.cementify.blogservice.codecs;



import com.cementify.blogservice.models.Blog;
import org.bson.*;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;


/**
 * Created by roshan on 16/03/16.
 */
public class BlogCodec implements CollectibleCodec<Blog>,GenericCodec {

    private CodecRegistry codecRegistry;

    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public BlogCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    public Blog generateIdIfAbsentFromDocument(Blog document) {
        if (!documentHasId(document)) {
            document.generateId();
        }
        return document;
    }

    public boolean documentHasId(Blog document) {
        if (document.getBlogId() == null) {
            return false;
        }
        return true;
    }

    public BsonValue getDocumentId(Blog document) {
        if (!documentHasId(document)) {
            throw new IllegalStateException("Document does not have id");
        }
        return new BsonString(document.getBlogId().toHexString());
    }


    @Override
    public void encode(BsonWriter writer,Blog value, EncoderContext encoderContext) {
        writeDocument(writer,value,encoderContext);
    }


    @Override
    public Blog decode(BsonReader reader, DecoderContext decoderContext) {
        Blog blog=new Blog();
        blog = (Blog) readDocument(reader, decoderContext, blog);
        return blog;
    }

    @Override
    public Class<Blog> getEncoderClass() {
        return Blog.class;
    }
}
