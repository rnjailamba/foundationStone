package com.cementify.blogservice.models;

import com.cementify.blogservice.customannotations.FieldName;

/**
 * Created by roshan on 19/03/16.
 */
public class Address {

    @FieldName(value = "phone")
    private String phoneNo;

    @FieldName(value = "ismale")
    private Boolean isMale;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(Boolean isMale) {
        this.isMale = isMale;
    }
}
