package com.cementify.blogservice.codecs;



import com.cementify.blogservice.models.ImageObject;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by roshan on 19/03/16.
 */
public class ImageObjectCodec implements Codec<ImageObject>,GenericCodec {
    private CodecRegistry codecRegistry;

    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public ImageObjectCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public void encode(BsonWriter writer, ImageObject value, EncoderContext encoderContext) {
        writeDocument(writer,value,encoderContext);
    }


    @Override
    public ImageObject decode(BsonReader reader, DecoderContext decoderContext) {
        ImageObject imageObject=new ImageObject();
        imageObject = (ImageObject) readDocument(reader, decoderContext, imageObject);
        return imageObject;
    }

    @Override
    public Class<ImageObject> getEncoderClass() {
        return ImageObject.class;
    }
}
