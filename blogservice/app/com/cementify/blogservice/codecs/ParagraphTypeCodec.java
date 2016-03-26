package com.cementify.blogservice.codecs;


import com.cementify.blogservice.models.Paragraph;
import com.cementify.blogservice.models.ParagraphType;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by roshan on 19/03/16.
 */
public class ParagraphTypeCodec implements Codec<ParagraphType>,GenericCodec {
    private CodecRegistry codecRegistry;

    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public ParagraphTypeCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public void encode(BsonWriter writer, ParagraphType value, EncoderContext encoderContext) {
        writeDocument(writer,value,encoderContext);
    }


    @Override
    public ParagraphType decode(BsonReader reader, DecoderContext decoderContext) {
        ParagraphType paragraphType =null;
        paragraphType = (ParagraphType) readDocument(reader, decoderContext, paragraphType);
        return paragraphType;
    }

    @Override
    public Class<ParagraphType> getEncoderClass() {
        return ParagraphType.class;
    }
}
