package com.cementify.userservice.models.request;

import com.cementify.userservice.models.CustomerEmail;

import java.util.List;

/**
 * Created by roshan on 01/03/16.
 */
public class CustomerEmailRequest {

    List<CustomerEmail> emails;

    public List<CustomerEmail> getEmails() {
        return emails;
    }

    public void setEmails(List<CustomerEmail> emails) {
        this.emails = emails;
    }
}
