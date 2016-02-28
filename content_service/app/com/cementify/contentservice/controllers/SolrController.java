package com.cementify.contentservice.controllers;

import com.cementify.contentservice.models.User;
import com.cementify.contentservice.services.SolrService;
import com.google.inject.Inject;
import org.apache.commons.lang3.text.FormatFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.FormFactory;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by roshan on 22/02/16.
 */
public class SolrController extends Controller{

    @Inject
    SolrService solrService;
    @Inject
    FormFactory formFactory;

    public Result ping() {
        Form<User> userForm=formFactory.form(User.class);
        User user=userForm.bindFromRequest().get();
        /*Properties props = new Properties();
        props.put("bootstrap.servers", "zoo1:9092");
        //props.put("acks", "0");
        //props.put("retries", "1");
        //props.put("batch.size", 1);
        //props.put("linger.ms", 1);
        //props.put("buffer.memory", 33554);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        try{
            for(int i = 0; i < 100; i++)
                producer.send(new ProducerRecord<String, String>("test", "whytesting","because first"));
        }catch (Exception e){
            e.getStackTrace();
        }


        producer.close();
        */
        return ok("Hello ContentService");
    }





}
