package com.cementify.blogservice.utils;

import com.cementify.blogservice.customannotations.FieldName;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by roshan on 18/03/16.
 */
public class ObjectAndDocumentFieldNameMappping {


    public static List<Field> getObjectFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            fields = getObjectFields(fields, type.getSuperclass());
        }

        return fields;
    }

    public static Map<String,String> getObjectDocumentFields(Map<String,String> fieldsMapping, Class<?> type) {
       Field[] fields=type.getDeclaredFields();
        if(fields==null)
            return null;
        for(Field field : fields){
            String name=field.getName();
            FieldName fieldName=field.getAnnotation(FieldName.class);
            String documentName =fieldName.value();
            fieldsMapping.put(name,documentName);
        }
        if (type.getSuperclass() != null) {
             fieldsMapping= getObjectDocumentFields(fieldsMapping, type.getSuperclass());
        }

        return fieldsMapping;
    }
    public static Map<String,FieldData> getDocumentObjectFields(Map<String,FieldData> fieldsMapping, Class<?> type) {
        Field[] fields=type.getDeclaredFields();
        if(fields==null)
            return null;
        for(Field field : fields){
            FieldData fieldData=new FieldData();
            String name=field.getName();
            FieldName fieldName=field.getAnnotation(FieldName.class);
            String documentName =fieldName.value();
            fieldData.setFieldName(name);
            fieldData.setField(field);
            fieldsMapping.put(documentName,fieldData);
        }
        if (type.getSuperclass() != null) {
            fieldsMapping= getDocumentObjectFields(fieldsMapping, type.getSuperclass());
        }

        return fieldsMapping;
    }

}
