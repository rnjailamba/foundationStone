package com.cementify.blogservice.codecproviders;

import com.cementify.blogservice.codecs.*;


import com.cementify.blogservice.models.*;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by roshan on 16/03/16.
 */
public class CustomCodecProvider implements CodecProvider {



    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if(clazz==Blog.class){
            return (Codec<T>) new BlogCodec(registry);
        }else if(clazz== PostedBy.class){
            return (Codec<T>) new PostedByCodec(registry);
        }else if(clazz== Paragraph.class) {
            return (Codec<T>) new ParagraphCodec(registry);
        }else if(clazz== Comment.class) {
            return (Codec<T>) new CommentCodec(registry);
        }else if(clazz== CommentCollection.class) {
            return (Codec<T>) new CommentCollectionCodec(registry);
        }else if(clazz== ParagraphType.class) {
            return (Codec<T>) new ParagraphTypeCodec(registry);
        }else if(clazz== ImageObject.class) {
            return (Codec<T>) new ImageObjectCodec(registry);
        }else if(clazz== VideoObject.class) {
            return (Codec<T>) new VideoObjectCodec(registry);
        }else if(clazz== UserAction.class) {
            return (Codec<T>) new UserActionCodec(registry);
        }else if(clazz== BlogAction.class) {
            return (Codec<T>) new BlogActionCodec(registry);
        }
        return  null;
    }


}
