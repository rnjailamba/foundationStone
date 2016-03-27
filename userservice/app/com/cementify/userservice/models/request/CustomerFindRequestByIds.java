package com.cementify.userservice.models.request;

import java.util.List;

/**
 * Created by roshan on 26/03/16.
 */
public class CustomerFindRequestByIds {

    List<Integer> customerIds;

    public List<Integer> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }
}
