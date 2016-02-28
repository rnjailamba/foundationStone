package com.cementify.userservice.models.mapping;

import com.cementify.userservice.models.Customer;
import com.cementify.userservice.models.CustomerDevice;
import com.cementify.userservice.models.request.CustomerRequest;
import com.cementify.userservice.models.response.CustomerResponse;
import com.cementify.userservice.models.response.CustomerResponseWithSocialAccount;

/**
 * Created by roshan on 31/01/16.
 */
public class CustomerMapping {

    public static CustomerResponse getCustomerResponse(Customer customer)
    {
        CustomerResponse customerResponse = new CustomerResponse();

        return getCustomerResponse(customer, customerResponse);

    }

    public static CustomerResponse getCustomerResponse(Customer customer,CustomerResponse customerResponse)
    {
        customerResponse.setCustomerId(customer.getCustomerId());
        customerResponse.setEmail(customer.getEmail());
        customerResponse.setIsVerified(customer.getIsVerified());
        customerResponse.setMobile(customer.getMobile());
        return customerResponse;
    }


    public static CustomerResponse getCustomerResponseFromCustomerDevice(CustomerDevice customerDevice){
        CustomerResponse customerResponse = new CustomerResponse();
        return getCustomerResponseFromCustomerDevice(customerDevice, customerResponse);
    }

    public static CustomerResponse getCustomerResponseWithSocialAccount(Customer customer)
    {
        CustomerResponseWithSocialAccount customerResponse = new CustomerResponseWithSocialAccount();
        return getCustomerResponseWithSocialAccount(customer, customerResponse);

    }

    public static CustomerResponse getCustomerResponseWithSocialAccount(Customer customer,CustomerResponseWithSocialAccount customerResponse)
    {
        customerResponse.setCustomerId(customer.getCustomerId());
        customerResponse.setEmail(customer.getEmail());
        customerResponse.setIsVerified(customer.getIsVerified());
        customerResponse.setMobile(customer.getMobile());
        customerResponse.setFbEmail(customer.getFbEmail());
        customerResponse.setFbId(customer.getFbId());
        customerResponse.setGoogleEmail(customer.getGoogleEmail());
        customerResponse.setGoogleId(customer.getGoogleId());
        return customerResponse;

    }


    private static CustomerResponse getCustomerResponseFromCustomerDevice(CustomerDevice customerDevice,CustomerResponse customerResponse){
        Customer customer = customerDevice.getCustomer();
        customerResponse.setCustomerId(customer.getCustomerId());
        customerResponse.setEmail(customer.getEmail());
        customerResponse.setIsVerified(customer.getIsVerified());
        customerResponse.setMobile(customer.getMobile());
        customerResponse.setRuid(customerDevice.getRuid());
        return customerResponse;
    }

  public static Customer getCustomerFromRequest(CustomerRequest customerRequest)
  {
      Customer customer = new Customer();
      return getCustomerFromRequest(customerRequest, customer);

  }

    public static Customer getCustomerFromRequest(CustomerRequest customerRequest,Customer customer)
    {
        customer.setCustomerId(customerRequest.getCustomerId());
        customer.setEmail(customerRequest.getEmail());
        customer.setFbEmail(customerRequest.getFbEmail());
        customer.setFbId(customerRequest.getFbId());
        customer.setGoogleEmail(customerRequest.getGoogleEmail());
        customer.setGoogleId(customerRequest.getGoogleId());
        customer.setIsVerified(customerRequest.getIsVerified());
        customer.setMobile(customerRequest.getMobile());
        customer.setPassword(customerRequest.getPassword());
        return customer;
    }
}
