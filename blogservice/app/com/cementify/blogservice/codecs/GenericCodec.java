package com.cementify.blogservice.codecs;

import com.cementify.blogservice.utils.FieldData;
import com.cementify.blogservice.utils.InvokeGetterSetter;
import com.cementify.blogservice.utils.ObjectAndDocumentFieldNameMappping;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by roshan on 19/03/16.
 */
public interface GenericCodec {

    default void writeDocument(BsonWriter writer, Object value, EncoderContext encoderContext){
        Map<String, String> objectDocumentMapping = new HashMap<String, String>();
        ObjectAndDocumentFieldNameMappping.getObjectDocumentFields(objectDocumentMapping, getEncoderClass());
        Set<String> objectField = objectDocumentMapping.keySet();
        Iterator<String> objectFieldIterator = objectField.iterator();
        writer.writeStartDocument();
        while (objectFieldIterator.hasNext()) {
            String fieldName = objectFieldIterator.next();
            String documentName=objectDocumentMapping.get(fieldName);
            Object object = InvokeGetterSetter.invokeGetter(value, fieldName);
            writeValue(writer,encoderContext,value,documentName);
        }
        writer.writeEndDocument();
    }


    default void writeArrayDocument(final BsonWriter writer, final Iterable<Object> list, final EncoderContext encoderContext,
                                    String documentName) {
        writer.writeStartArray(documentName);
        for (final Object value : list) {
            writeValue(writer, encoderContext, value,documentName);
        }
        writer.writeEndArray();
    }


    default void writeValue(final BsonWriter writer, final EncoderContext encoderContext, final Object value,
                            String documentName) {
        if (value == null) {
            writer.writeNull(documentName);
        } else if (Iterable.class.isAssignableFrom(value.getClass())) {
            writeArrayDocument(writer, (Iterable<Object>) value, encoderContext.getChildContext(),documentName);
        } else if (value instanceof String) {
            writer.writeString(documentName, (String) value);
        } else if (value instanceof Boolean) {
            writer.writeBoolean(documentName, (Boolean) value);
        } else if (value instanceof Integer) {
            writer.writeInt32(documentName, (Integer) value);
        } else if (value instanceof Long) {
            writer.writeInt64(documentName, (Long) value);
        } else if (value instanceof ObjectId) {
            writer.writeObjectId(documentName, (ObjectId) value);
        } else if (value instanceof Date) {
            writer.writeDateTime(documentName, ((Date)value).getTime());
        } else {
            writer.writeName(documentName);
            Codec codec = getCodecRegistry().get(value.getClass());
            encoderContext.encodeWithChildContext(codec, writer, value);
        }
    }

    default Object readDocument(BsonReader reader, DecoderContext decoderContext, Object object) {
        Map<String, FieldData> objectDocumentMapping = new HashMap<String, FieldData>();
        ObjectAndDocumentFieldNameMappping.getDocumentObjectFields(objectDocumentMapping, getEncoderClass());
        reader.readStartDocument();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String documentName = reader.readName();
            FieldData fieldData = objectDocumentMapping.get(documentName);
            String fieldName = fieldData.getFieldName();
            Field field = fieldData.getField();
            Object value = readValue(reader, decoderContext, field);
            InvokeGetterSetter.invokeSetter(object, fieldName, value);
        }
        reader.readEndDocument();
        return object;
    }

    default Object readValue(BsonReader reader, DecoderContext decoderContext, Field field) {
        switch (reader.getCurrentBsonType()) {
            case INT32:
                Integer intValue = reader.readInt32();
                return intValue;
            case INT64:
                Long longValue = reader.readInt64();
                return longValue;
            case STRING:
                String stringValue = reader.readString();
                return stringValue;
            case OBJECT_ID:
                ObjectId objectId = reader.readObjectId();
                return objectId;
            case BOOLEAN:
                Boolean booleanValue = reader.readBoolean();
                return booleanValue;
            case DATE_TIME:
                Date dateValue = new Date(reader.readDateTime());
                return dateValue;
            case DOCUMENT:
                Object embeddedObject = getCodecRegistry().get(field.getClass()).decode(reader, decoderContext);
                return embeddedObject;
            case ARRAY:
                List<Object> embeddedList = readArrayDocument(reader, decoderContext, field);
                return embeddedList;
            case NULL:
                reader.readNull();
                return null;
        }
        reader.skipValue();
        return null;
    }

    default List<Object> readArrayDocument(BsonReader reader, DecoderContext decoderContext, Field field) {
        List<Object> listObject = new ArrayList<>();
        reader.readStartArray();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            Object value = readValue(reader, decoderContext, field);
            listObject.add(value);
        }
        reader.readEndArray();
        return listObject;
    }

    Class<?> getEncoderClass();

    CodecRegistry getCodecRegistry();
}
