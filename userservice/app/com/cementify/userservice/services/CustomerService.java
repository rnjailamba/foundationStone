package com.cementify.userservice.services;

import com.cementify.userservice.models.Customer;
import com.cementify.userservice.models.CustomerDevices;
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
    Customer update(Integer customerId,Customer customer);
    boolean updatePassword(CustomerResetPasswordRequest customerResetPasswordRequest);
    boolean updateFbId(String mobile, String fbId, String fbEmail);
    boolean updateGoogId(String mobile,String email, String googId);
    boolean setVerified(String mobile);
    Customer findByFbId(String fbId);
    Customer findByEmail(String email);
    Customer findCustomerByCustomerId(int customerId);
    Customer createCustomer(CustomerRequest customerRequest);

}
