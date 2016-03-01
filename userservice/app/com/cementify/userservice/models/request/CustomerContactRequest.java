package com.cementify.userservice.models.request;

import com.cementify.userservice.models.CustomerContact;

import java.util.List;

/**
 * Created by roshan on 01/03/16.
 */
public class CustomerContactRequest {

    public List<CustomerContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<CustomerContact> contacts) {
        this.contacts = contacts;
    }

    List<CustomerContact> contacts;


}
