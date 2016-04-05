package com.cementify.blogservice.codecs;


import com.cementify.blogservice.models.BlogAction;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by roshan on 05/04/16.
 */
public class BlogActionCodec  implements Codec<BlogAction>,GenericCodec {
    private CodecRegistry codecRegistry;

    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public  BlogActionCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public void encode(BsonWriter writer,  BlogAction value, EncoderContext encoderContext) {
        writeDocument(writer,value,encoderContext);
    }


    @Override
    public  BlogAction decode(BsonReader reader, DecoderContext decoderContext) {
        BlogAction blogAction=new  BlogAction();
        blogAction = (BlogAction) readDocument(reader, decoderContext, blogAction);
        return blogAction;
    }

    @Override
    public Class<BlogAction> getEncoderClass() {
        return BlogAction.class;
    }
}
