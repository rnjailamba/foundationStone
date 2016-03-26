package com.cementify.blogservice.codecs;



import com.cementify.blogservice.models.VideoObject;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by roshan on 19/03/16.
 */
public class VideoObjectCodec implements Codec<VideoObject>,GenericCodec {
    private CodecRegistry codecRegistry;

    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public VideoObjectCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public void encode(BsonWriter writer, VideoObject value, EncoderContext encoderContext) {
        writeDocument(writer,value,encoderContext);
    }


    @Override
    public VideoObject decode(BsonReader reader, DecoderContext decoderContext) {
        VideoObject videoObject=new VideoObject();
        videoObject = (VideoObject) readDocument(reader, decoderContext, videoObject);
        return videoObject;
    }

    @Override
    public Class<VideoObject> getEncoderClass() {
        return VideoObject.class;
    }
}
