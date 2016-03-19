package com.cementify.blogservice.utils;

import com.cementify.blogservice.customannotations.FieldName;
import com.cementify.blogservice.customannotations.Id;

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

    public static IdAndFieldData getObjectDocumentFieldMappings(IdAndFieldData idAndFieldData, Class<?> type) {
        Map<String,String> fieldsMapping=idAndFieldData.getFieldDatas();
        IdData idData=idAndFieldData.getIdData();
        Field[] fields=type.getDeclaredFields();
        if(fields==null)
            return null;
        for(Field field : fields){
            String documentName=null;
            String idDocumentName=null;
            String name=field.getName();
            FieldName fieldName=field.getAnnotation(FieldName.class);
            if(fieldName!=null)
            documentName =fieldName.value();
            Id id=field.getAnnotation(Id.class);
            if(id!=null){
                idDocumentName= id.value();
                idData.setDocumentIdName(idDocumentName);
                idData.setIdFieldName(name);
            }
            if(documentName!=null)
            fieldsMapping.put(name,documentName);
        }
        if (type.getSuperclass() != null) {
             idAndFieldData= getObjectDocumentFieldMappings(idAndFieldData, type.getSuperclass());
        }

        return idAndFieldData;
    }
    public static Map<String,FieldData> getDocumentObjectFieldMappings(Map<String,FieldData> fieldsMapping, Class<?> type) {
        Field[] fields=type.getDeclaredFields();
        if(fields==null)
            return null;
        for(Field field : fields){
            String documentName=null;
            FieldData fieldData=new FieldData();
            String name=field.getName();
            FieldName fieldName=field.getAnnotation(FieldName.class);
            if(fieldName!=null)
            documentName=fieldName.value();
            fieldData.setFieldName(name);
            fieldData.setField(field);
            Id id=field.getAnnotation(Id.class);
            if(id!=null){
                documentName= id.value();
            }
            if(documentName!=null)
            fieldsMapping.put(documentName,fieldData);
        }
        if (type.getSuperclass() != null) {
            fieldsMapping= getDocumentObjectFieldMappings(fieldsMapping, type.getSuperclass());
        }

        return fieldsMapping;
    }



}
