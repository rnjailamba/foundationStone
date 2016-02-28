package com.cementify.userservice.models.mapping;

import com.cementify.userservice.models.Customer;
import com.cementify.userservice.models.CustomerDevices;
import com.cementify.userservice.models.request.CustomerRequest;
import com.cementify.userservice.models.response.CustomerResponse;

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
        customerResponse.setFbEmail(customer.getFbEmail());
        customerResponse.setFbId(customer.getFbId());
        customerResponse.setGoogleEmail(customer.getGoogleEmail());
        customerResponse.setGoogleId(customer.getGoogleId());
        customerResponse.setIsVerified(customer.getIsVerified());
        customerResponse.setMobile(customer.getMobile());
        return customerResponse;
        //customerResponse.setRuid(customer.getRuid());
    }


    public static CustomerResponse getCustomerResponseFromCustomerDevice(CustomerDevices customerDevices){
        CustomerResponse customerResponse = new CustomerResponse();

        return getCustomerResponseFromCustomerDevice(customerDevices, customerResponse);
    }

    private static CustomerResponse getCustomerResponseFromCustomerDevice(CustomerDevices customerDevices,CustomerResponse customerResponse){
        Customer customer =customerDevices.getCustomer();
        customerResponse.setCustomerId(customer.getCustomerId());
        customerResponse.setEmail(customer.getEmail());
        customerResponse.setFbEmail(customer.getFbEmail());
        customerResponse.setFbId(customer.getFbId());
        customerResponse.setGoogleEmail(customer.getGoogleEmail());
        customerResponse.setGoogleId(customer.getGoogleId());
        customerResponse.setIsVerified(customer.getIsVerified());
        customerResponse.setMobile(customer.getMobile());
        customerResponse.setRuid(customerDevices.getRuid());
        return customerResponse;
    }
  /*  public CustomerSearchResponse getSearchResponse(Customer customer)
    {
        CustomerSearchResponse customerSearchResponse = new CustomerSearchResponse();

        getCustomerResponse(customer, CustomerSearchResponse);
        customerSearchResponse.setCreatedDate(customer.getCreatedDate());

        return customerSearchResponse;
    }
*/
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
        //customer.setRuid(customerRequest.getRuid());
        customer.setPassword(customerRequest.getPassword());
        return customer;
    }
}
