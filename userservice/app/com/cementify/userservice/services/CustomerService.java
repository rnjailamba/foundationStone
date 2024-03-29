package com.cementify.userservice.services;

import com.cementify.userservice.models.*;
import com.cementify.userservice.models.request.CustomerDataRequest;
import com.cementify.userservice.models.request.CustomerDeviceRequest;
import com.cementify.userservice.models.request.CustomerRequest;
import com.cementify.userservice.models.request.CustomerResetPasswordRequest;
import com.cementify.userservice.models.response.CustomerResponseData;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * Created by roshan on 30/01/16.
 */
@ImplementedBy(CustomerServiceImp.class)
public interface CustomerService {

    CustomerDevice resetPassword(CustomerDeviceRequest customerRequest);
    CustomerDevice createRuid(CustomerDeviceRequest customerRequest);
    CustomerDevice createAccount(CustomerDeviceRequest customerRequest);
    Customer findByMobile(String mobile);
    Customer update(CustomerDeviceRequest customerDeviceRequest);
    boolean updatePassword(CustomerResetPasswordRequest customerResetPasswordRequest);
    void updateFbId(CustomerRequest customerRequest);
    void updateGoogId(CustomerRequest customerRequest);
    void setVerified(CustomerRequest customerRequest);
    Customer findByFbId(String fbId);
    Customer findByEmail(String email);
    Customer findCustomerByCustomerId(int customerId);
    List<Customer> findCustomerByCustomerIds(List<Integer> customerIds);
    Customer createCustomer(CustomerDeviceRequest customerRequest);
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
    List<CustomerResponseData>  findCustomerDataByCustomerIds(List<Integer> customerIds);
    void addCustomerData(CustomerDataRequest customerDataRequest);
    void updateCustomerData(CustomerDataRequest customerDataRequest);
    CustomerData findCustomerDataByCustomerId(Integer customerId);
}
