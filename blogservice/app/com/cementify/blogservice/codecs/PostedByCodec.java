package com.cementify.blogservice.codecs;


import com.cementify.blogservice.models.PostedBy;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by roshan on 19/03/16.
 */
public class PostedByCodec implements Codec<PostedBy>,GenericCodec {
    private CodecRegistry codecRegistry;

    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public PostedByCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public void encode(BsonWriter writer, PostedBy value, EncoderContext encoderContext) {
        writeDocument(writer,value,encoderContext);
    }


    @Override
    public PostedBy decode(BsonReader reader, DecoderContext decoderContext) {
        PostedBy postedBy=new PostedBy();
        postedBy = (PostedBy) readDocument(reader, decoderContext, postedBy);
        return postedBy;
    }

    @Override
    public Class<PostedBy> getEncoderClass() {
        return PostedBy.class;
    }
}
