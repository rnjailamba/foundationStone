package com.cementify.userservice.services;

import com.cementify.userservice.models.Customer;
import com.cementify.userservice.models.CustomerDevices;
import com.cementify.userservice.models.request.CustomerDataUpdateRequest;
import com.cementify.userservice.models.request.CustomerRequest;
import com.cementify.userservice.models.request.CustomerResetPasswordRequest;
import com.google.inject.ImplementedBy;

/**
 * Created by roshan on 30/01/16.
 */
@ImplementedBy(CustomerServiceImp.class)
public interface CustomerService {

    CustomerDevices create(CustomerRequest customerRequest);
    CustomerDevices createRuid(CustomerRequest customerRequest);
    CustomerDevices createAccount(CustomerRequest customerRequest);
    Customer findByMobile(String mobile);
    Customer update(CustomerDataUpdateRequest customerDataUpdateRequest);
    boolean updatePassword(CustomerResetPasswordRequest customerResetPasswordRequest);
    void updateFbId(CustomerRequest customerRequest);
    void updateGoogId(CustomerRequest customerRequest);
    void setVerified(CustomerRequest customerRequest);
    Customer findByFbId(String fbId);
    Customer findByEmail(String email);
    Customer findCustomerByCustomerId(int customerId);
    Customer createCustomer(CustomerRequest customerRequest);

}
