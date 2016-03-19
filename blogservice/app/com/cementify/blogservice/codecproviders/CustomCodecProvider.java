package com.cementify.blogservice.codecproviders;

import com.cementify.blogservice.codecs.AddressCodec;
import com.cementify.blogservice.codecs.UserCodec;
import com.cementify.blogservice.models.Address;
import com.cementify.blogservice.models.User;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by roshan on 16/03/16.
 */
public class CustomCodecProvider implements CodecProvider {



    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if(clazz==User.class){
            return (Codec<T>) new UserCodec(registry);
        }else if(clazz== Address.class){
            return (Codec<T>) new AddressCodec(registry);
        }
        return  null;
    }
}
