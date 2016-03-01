package com.cementify.userservice.models.request;

import com.cementify.userservice.models.CustomerAddress;

import java.util.List;

/**
 * Created by roshan on 01/03/16.
 */
public class CustomerAddressRequest {

    List<CustomerAddress> addresses;

    public List<CustomerAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<CustomerAddress> addresses) {
        this.addresses = addresses;
    }
}
