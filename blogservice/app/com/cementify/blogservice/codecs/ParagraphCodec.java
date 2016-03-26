package com.cementify.blogservice.codecs;


import com.cementify.blogservice.models.Paragraph;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by roshan on 19/03/16.
 */
public class ParagraphCodec implements Codec<Paragraph>,GenericCodec {
    private CodecRegistry codecRegistry;

    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public ParagraphCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public void encode(BsonWriter writer, Paragraph value, EncoderContext encoderContext) {
        writeDocument(writer,value,encoderContext);
    }


    @Override
    public Paragraph decode(BsonReader reader, DecoderContext decoderContext) {
        Paragraph paragraph=new Paragraph();
        paragraph = (Paragraph) readDocument(reader, decoderContext, paragraph);
        return paragraph;
    }

    @Override
    public Class<Paragraph> getEncoderClass() {
        return Paragraph.class;
    }
}
