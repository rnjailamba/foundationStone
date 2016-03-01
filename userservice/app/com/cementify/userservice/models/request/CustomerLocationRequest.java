package com.cementify.userservice.models.request;

import com.cementify.userservice.models.CustomerLocation;

import java.util.List;

/**
 * Created by roshan on 01/03/16.
 */
public class CustomerLocationRequest {

    List<CustomerLocation> locations;

    public List<CustomerLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<CustomerLocation> locations) {
        this.locations = locations;
    }
}
