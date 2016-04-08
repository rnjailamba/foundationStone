package com.cementify.userservice.models.mapping;

import com.cementify.userservice.models.Customer;
import com.cementify.userservice.models.CustomerDevice;
import com.cementify.userservice.models.CustomerData;
import com.cementify.userservice.models.request.CustomerRequest;
import com.cementify.userservice.models.response.CustomerResponse;
import com.cementify.userservice.models.response.CustomerResponseData;
import com.cementify.userservice.models.response.CustomerResponseWithSocialAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roshan on 31/01/16.
 */
public class CustomerMapping {

    public static CustomerResponse getCustomerResponse(Customer customer)
    {
        CustomerResponse customerResponse = new CustomerResponse();

        return getCustomerResponse(customer, customerResponse);

    }

    public static List<CustomerResponse> getCustomerResponseList(List<Customer> customers)
    {
        List<CustomerResponse> customersResponse =new ArrayList<>();
        if(customers == null)
              return null;
        return getCustomerResponseList(customers, customersResponse);

    }

    public static List<CustomerResponse> getCustomerResponseList(
            List<Customer> customers,List<CustomerResponse> customersResponse)
    {
        if(customers ==null){
            return customersResponse;
        }
        for(Customer customer :customers){
            CustomerResponse customerResponse =new CustomerResponse();
            customerResponse.setCustomerId(customer.getCustomerId());
            customerResponse.setEmail(customer.getEmail());
            customerResponse.setIsVerified(customer.getIsVerified());
            customersResponse.add(customerResponse);
        }
        return customersResponse;
    }

    public static List<CustomerResponseData> getCustomerDataResponseList(List<CustomerData> customersData)
    {
        List<CustomerResponseData> customerResponseData =new ArrayList<>();
        if(customersData == null)
            return null;
        return getCustomerDataResponseList(customersData, customerResponseData);

    }

    public static List<CustomerResponseData> getCustomerDataResponseList(
            List<CustomerData> customersData,List<CustomerResponseData> customersResponseData)
    {
        if(customersData ==null){
            return customersResponseData;
        }
        for(CustomerData customerData :customersData){
            CustomerResponseData customerResponseData =new CustomerResponseData();
            customerResponseData.setCustomerId(customerData.getCustomerId());
            customerResponseData.setAboutUser(customerData.getAboutUser());
            customerResponseData.setBirthday(customerData.getBirthday());
            customerResponseData.setAge(customerData.getAge());
            customerResponseData.setIsMale(customerData.getIsMale());
            customerResponseData.setProfilePic(customerData.getProfilePic());
            customersResponseData.add(customerResponseData);
        }
        return customersResponseData;
    }


    public static CustomerResponse getCustomerResponse(Customer customer,CustomerResponse customerResponse)
    {
        customerResponse.setCustomerId(customer.getCustomerId());
        customerResponse.setEmail(customer.getEmail());
        customerResponse.setIsVerified(customer.getIsVerified());
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


    public static CustomerResponseData getCustomerDataResponse(CustomerData customersData)
    {
        CustomerResponseData customerResponseData =new CustomerResponseData();
        if(customersData == null)
            return null;
        return getCustomerDataResponse(customersData, customerResponseData);

    }

    public static CustomerResponseData getCustomerDataResponse(
            CustomerData customerData,CustomerResponseData customerResponseData)
    {
        if(customerData ==null){
            return customerResponseData;
        }
            customerResponseData.setCustomerId(customerData.getCustomerId());
            customerResponseData.setAboutUser(customerData.getAboutUser());
            customerResponseData.setBirthday(customerData.getBirthday());
            customerResponseData.setAge(customerData.getAge());
            customerResponseData.setIsMale(customerData.getIsMale());
            customerResponseData.setProfilePic(customerData.getProfilePic());

        return customerResponseData;
    }
}
