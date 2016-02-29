package com.cementify.userservice.services;

import com.cementify.userservice.models.*;
import com.cementify.userservice.models.request.CustomerDataRequest;
import com.cementify.userservice.models.request.CustomerRequest;
import com.cementify.userservice.models.request.CustomerResetPasswordRequest;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * Created by roshan on 30/01/16.
 */
@ImplementedBy(CustomerServiceImp.class)
public interface CustomerService {

    CustomerDevice create(CustomerDataRequest customerRequest);
    CustomerDevice createRuid(CustomerDataRequest customerRequest);
    CustomerDevice createAccount(CustomerDataRequest customerRequest);
    Customer findByMobile(String mobile);
    Customer update(CustomerDataRequest customerDataRequest);
    boolean updatePassword(CustomerResetPasswordRequest customerResetPasswordRequest);
    void updateFbId(CustomerRequest customerRequest);
    void updateGoogId(CustomerRequest customerRequest);
    void setVerified(CustomerRequest customerRequest);
    Customer findByFbId(String fbId);
    Customer findByEmail(String email);
    Customer findCustomerByCustomerId(int customerId);
    Customer createCustomer(CustomerRequest customerRequest);
    void removeRuid(CustomerRequest customerRequest);
    CustomerDevice findByRuid(String ruid);
    List<CustomerAddress> findAddressesByCustomerId(Integer customerId);
    List<CustomerLocation> findLocationsByCustomerId(Integer customerId);
    List<CustomerContact> findContactsByCustomerId(Integer customerId);
    List<CustomerEmail> findEmailsByCustomerId(Integer customerId);
    void addEmail(CustomerEmail customerEmail);
    void addAddress(CustomerAddress customerAddress);
    void addLocation(CustomerLocation customerLocation);
    void addContact(CustomerContact customerContact);
}
