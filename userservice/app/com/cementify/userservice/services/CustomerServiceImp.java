package com.cementify.userservice.services;

import com.cementify.userservice.exceptions.EntityConflictException;
import com.cementify.userservice.exceptions.EntityNotFoundException;
import com.cementify.userservice.exceptions.InvalidStateException;
import com.cementify.userservice.exceptions.NotAuthenticatedException;
import com.cementify.userservice.models.*;
import com.cementify.userservice.models.mapping.CustomerMapping;
import com.cementify.userservice.models.request.CustomerDataRequest;
import com.cementify.userservice.models.request.CustomerDeviceRequest;
import com.cementify.userservice.models.request.CustomerRequest;
import com.cementify.userservice.models.request.CustomerResetPasswordRequest;
import com.cementify.userservice.models.response.CustomerResponseData;
import play.Logger;
import play.db.jpa.JPA;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by roshan on 30/01/16.
 */
public class CustomerServiceImp implements CustomerService {


    @Override
    public CustomerDevice resetPassword(CustomerDeviceRequest customerRequest) {
        return createAccount(customerRequest);
    }

    @Override
    public Customer createCustomer(CustomerDeviceRequest customerRequest) throws EntityConflictException {
        Customer customer=findByMobile(customerRequest.getMobile());
        if(customer==null){
            sendWelcomeMail(customerRequest);
            customer = CustomerMapping.getCustomerFromRequest(customerRequest);
            hashAndSavePassword(customer);
        }else{
            customer.setPassword(customerRequest.getPassword());
            if(customerRequest.getEmail()!=null)
            customer.setEmail(customerRequest.getEmail());
            hashAndSavePassword(customer);
        }
        return customer;
    }

    @Override
    public CustomerDevice createAccount(CustomerDeviceRequest customerRequest) {
        Customer customer = createCustomer(customerRequest);
        UUID ruid = UUID.randomUUID();
        CustomerDevice customerDevice = new CustomerDevice();
        customerDevice.setCustomer(customer);
        customerDevice.setDeviceId(customerRequest.getDeviceId());
        customerDevice.setOsType(customerRequest.getOsType());
        customerDevice.setNotificationId(customerRequest.getNotificationId());
        customerDevice.setRuid(ruid.toString());
        JPA.em().persist(customerDevice);
        return customerDevice;
    }

    @Override
    public CustomerDevice createRuid(CustomerDeviceRequest customerRequest) {
        Customer customer = findByMobile(customerRequest.getMobile());
        if (customer == null) {
            throw new EntityNotFoundException("Customer with "
                    + customerRequest.getMobile() + " not found.");
        }
        if (customer.hasPassword(customerRequest.getPassword())) {
            UUID ruid = UUID.randomUUID();
            CustomerDevice customerDevice = new CustomerDevice();
            customerDevice.setCustomer(customer);
            customerDevice.setDeviceId(customerRequest.getDeviceId());
            customerDevice.setOsType(customerRequest.getOsType());
            customerDevice.setNotificationId(customerRequest.getNotificationId());
            customerDevice.setRuid(ruid.toString());
            JPA.em().persist(customerDevice);
            return customerDevice;
        } else {
            throw new NotAuthenticatedException("Customer with "
                    + customer.getMobile() + " not autheticated.");
        }

    }

    @Override
    public Customer update(CustomerDeviceRequest customerRequest) {
        Customer toUpdate = findCustomerByCustomerId(customerRequest.getCustomerId());
        if (toUpdate == null) {
            throw new EntityNotFoundException("Customer with id "
                    + customerRequest.getCustomerId() + " not found.");
        }
        toUpdate.setEmail(customerRequest.getEmail());
        toUpdate.setFbEmail(customerRequest.getFbEmail());
        toUpdate.setFbId(customerRequest.getFbId());
        toUpdate.setGoogleEmail(customerRequest.getGoogleEmail());
        toUpdate.setGoogleId(customerRequest.getGoogleId());
        toUpdate.setUserName(customerRequest.getFirstName());
        toUpdate.setIsVerified(customerRequest.getIsVerified());
        toUpdate.setIsCodBlocked(customerRequest.getIsCodBlocked());
        toUpdate.setIsPrepaidBlocked(customerRequest.getIsPrepaidBlocked());
        JPA.em().persist(toUpdate);
        return toUpdate;
    }

    @Override
    public boolean updatePassword(CustomerResetPasswordRequest customerResetPasswordRequest) {
        Customer customer = findCustomerByCustomerId(customerResetPasswordRequest.getCustomerId());
        if (customer == null) {
            throw new EntityNotFoundException("Customer with mobile #  " + customerResetPasswordRequest.getCustomerId()
                    + " not found.");
        }
        boolean oldPasswordCheck = customer.hasPassword(customerResetPasswordRequest.getOldPassword());
        if (oldPasswordCheck) {
            customer.setPassword(customerResetPasswordRequest.getPassword());
            hashAndSavePassword(customer);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void updateFbId(CustomerRequest customerRequest) {
        Customer customer = findCustomerByCustomerId(customerRequest.getCustomerId());
        if (customer == null) {
            throw new EntityNotFoundException("Customer with "
                    + customerRequest.getCustomerId() + " not found.");
        }
        customer.setFbId(customerRequest.getFbId());
        customer.setFbEmail(customerRequest.getFbEmail());
        JPA.em().persist(customer);
    }

    @Override
    public void updateGoogId(CustomerRequest customerRequest) {
        Customer customer = findCustomerByCustomerId(customerRequest.getCustomerId());
        if (customer == null) {
            throw new EntityNotFoundException("Customer with "
                    + customerRequest.getCustomerId() + " not found.");
        }
        customer.setGoogleEmail(customerRequest.getGoogleEmail());
        customer.setGoogleId(customerRequest.getGoogleId());
        JPA.em().persist(customer);
    }

    @Override
    public void setVerified(CustomerRequest customerRequest) {
        Customer customer = findCustomerByCustomerId(customerRequest.getCustomerId());
        if (customer == null) {
            throw new EntityNotFoundException("Customer with "
                    + customerRequest.getCustomerId() + " not found.");
        }
        customer.setIsVerified(customerRequest.getIsVerified());
        JPA.em().persist(customer);

    }


    @Override
    public Customer findByFbId(String fbId) {
        Query query = JPA.em()
                .createQuery("select c from Customer c where c.fbId= :fbId");
        query.setParameter("fbId", fbId);
        return executeQuery(query);

    }


    private void sendWelcomeMail(CustomerRequest customerRequest) {

    }

    @Override
    public Customer findByMobile(String mobile) {
        Query query = JPA.em()
                .createQuery("select c from Customer c where c.mobile= :mobile");
        query.setParameter("mobile", mobile);
        return executeQuery(query);
    }

    private Customer executeQuery(Query query) {
        List resultList = query.getResultList();

        if (resultList ==null || resultList.isEmpty()) {
            return null;
        }
        if (resultList.size() != 1) {
            throw new InvalidStateException("Found more than one user");
        }
        Customer customer = (Customer) resultList.get(0);
        return customer;
    }


    private void hashAndSavePassword(Customer customer) {
        customer.encryptPassword();
        JPA.em().persist(customer);
    }

    @Override
    public Customer findByEmail(String email) {
        Query query = JPA.em()
                .createQuery("select c from Customer c where c.email= :email");
        query.setParameter("email", email);
        return executeQuery(query);
    }

    @Override
    public CustomerDevice findByRuid(String ruid) {
        Query query = JPA.em()
                .createQuery("select c from CustomerDevice c where c.ruid= :ruid");
        query.setParameter("ruid", ruid);
        List resultList = query.getResultList();

        if (resultList.isEmpty()) {
            throw new EntityNotFoundException("Customer with "
                    + ruid + " not found.");
        }
        if (resultList.size() != 1) {
            throw new InvalidStateException("Found more than one user");
        }
        CustomerDevice customerDevice = (CustomerDevice) resultList.get(0);
        return customerDevice;

    }


    @Override
    public Customer findCustomerByCustomerId(int customerId) {
        return JPA.em().find(Customer.class, customerId);
    }

    @Override
    public List<Customer> findCustomerByCustomerIds(List<Integer> customerIds) {

        Query query = JPA.em().createQuery("select c from Customer c where c.customerId in (:customerIds)");
        query.setParameter("customerIds", customerIds);
        List resultList = query.getResultList();
        if (resultList.isEmpty()) {
            throw new EntityNotFoundException("Customer with "
                    + customerIds + " not found.");
        }
        List<Customer> customerList =new ArrayList<>();
        for(int i=0;i<resultList.size();i++){
            Customer customer=(Customer) resultList.get(i);
            customerList.add(customer);
        }
        return customerList;
    }

    @Override
    public List<CustomerResponseData> findCustomerDataByCustomerIds(List<Integer> customerIds){
        Query query = JPA.em().createQuery("select new com.cementify.userservice.models.response.CustomerResponseData(" +
                "c.customerId,cd.birthday,cd.age,cd.aboutUser,cd.profilePic,cd.isMale,c.userName)" +
                " from CustomerData cd join Customer c where  c.customerId =cd.customerId" +
                " AND c.customerId in (:customerIds)");
        query.setParameter("customerIds", customerIds);
        List<CustomerResponseData> resultList = (List<CustomerResponseData>)query.getResultList();
        if (resultList ==null || resultList.isEmpty()) {
            throw new EntityNotFoundException("Customer with "
                    + customerIds + " not found.");
        }
        return resultList;
    }

    @Override
    public void removeRuid(CustomerRequest customerRequest) {
        CustomerDevice customerDevice=findByRuid(customerRequest.getRuid());
        if (customerRequest.getCustomerId().equals(customerDevice.getCustomer().getCustomerId())){
           JPA.em().remove(customerDevice);
        }else {
            throw new NotAuthenticatedException("CustomerId conflict Exception");
        }
    }

    @Override
    public List<CustomerAddress> findAddressesByCustomerId(Integer customerId){
        Query query = JPA.em()
                .createQuery("select c from CustomerAddress c where c.customerId= :customerId");
        query.setParameter("customerId", customerId);
        List resultList = query.getResultList();

        if (resultList ==null || resultList.isEmpty()) {
            throw new EntityNotFoundException("Customer Address with "
                    + customerId + " not found.");
        }
        List<CustomerAddress> customerAddresses=new ArrayList<>();
        int i=0;
        while(i<resultList.size()) {
            CustomerAddress customerAddress = (CustomerAddress) resultList.get(i);
            customerAddresses.add(customerAddress);
            i++;
        }
       return customerAddresses;
    }

    @Override
    public List<CustomerLocation> findLocationsByCustomerId(Integer customerId){
        Query query = JPA.em()
                .createQuery("select c from CustomerLocation c where c.customerId= :customerId");
        query.setParameter("customerId", customerId);
        List resultList = query.getResultList();

        if (resultList ==null ||resultList.isEmpty()) {
            throw new EntityNotFoundException("Customer Location with "
                    + customerId + " not found.");
        }
        List<CustomerLocation> customerLocations=new ArrayList<>();
        int i=0;
        while(i<resultList.size()) {
            CustomerLocation customerLocation = (CustomerLocation) resultList.get(i);
            customerLocations.add(customerLocation);
            i++;
        }
        return customerLocations;
    }

    @Override
    public List<CustomerContact> findContactsByCustomerId(Integer customerId){
        Query query = JPA.em()
                .createQuery("select c from CustomerContact c where c.customerId= :customerId");
        query.setParameter("customerId", customerId);
        List resultList = query.getResultList();

        if (resultList ==null ||resultList.isEmpty()) {
            throw new EntityNotFoundException("Customer contacts with "
                    + customerId + " not found.");
        }
        List<CustomerContact> customerContactList=new ArrayList<>();
        int i=0;
        while(i<resultList.size()) {
            CustomerContact customerContact = (CustomerContact) resultList.get(i);
            customerContactList.add(customerContact);
            i++;
        }
        return customerContactList;
    }

    @Override
    public List<CustomerEmail> findEmailsByCustomerId(Integer customerId){
        Query query = JPA.em()
                .createQuery("select c from CustomerEmail c where c.customerId= :customerId");
        query.setParameter("customerId", customerId);
        List resultList = query.getResultList();

        if (resultList ==null ||resultList.isEmpty()) {
            throw new EntityNotFoundException("Customer emails with "
                    + customerId + " not found.");
        }
        List<CustomerEmail> customerEmailList=new ArrayList<>();
        int i=0;
        while(i<resultList.size()) {
            CustomerEmail customerEmail = (CustomerEmail) resultList.get(i);
            customerEmailList.add(customerEmail);
            i++;
        }
        return customerEmailList;
    }

    @Override
    public void addEmail(CustomerEmail customerEmail) {
        JPA.em().persist(customerEmail);
    }

    @Override
    public void addAddress(CustomerAddress customerAddress) {
        JPA.em().persist(customerAddress);
    }

    @Override
    public void addContact(CustomerContact customerContact) {
        JPA.em().persist(customerContact);
    }

    @Override
    public void addLocation(CustomerLocation customerLocation) {
        JPA.em().persist(customerLocation);
    }

    @Override
    public void addCustomerData(CustomerDataRequest customerDataRequest){
        CustomerData customerData = CustomerMapping.getCustomerDataFromCustomerDataRequest(customerDataRequest);
        JPA.em().persist(customerData);
        if(customerDataRequest.getUserName() !=null && (!customerDataRequest.getUserName().isEmpty())){
            Customer customer =new Customer();
            customer.setCustomerId(customerDataRequest.getCustomerId());
            customer.setUserName(customerDataRequest.getUserName());
            JPA.em().merge(customer);
        }


    }

    @Override
    public void updateCustomerData(CustomerDataRequest customerDataRequest){
        CustomerData customerData = findCustomerDataByCustomerId(customerDataRequest.getCustomerId());
        customerData.setAboutUser(customerDataRequest.getAboutUser());
        customerData.setAge(customerDataRequest.getAge());
        customerData.setBirthday(customerDataRequest.getBirthday());
        customerData.setIsMale(customerDataRequest.getIsMale());
        customerData.setProfilePic(customerDataRequest.getProfilePic());
        JPA.em().merge(customerDataRequest);
        if(customerDataRequest.getUserName() !=null && (!customerDataRequest.getUserName().isEmpty())){
            Customer customer =new Customer();
            customer.setCustomerId(customerDataRequest.getCustomerId());
            customer.setUserName(customerDataRequest.getUserName());
            JPA.em().merge(customer);
        }

    }

    @Override
    public CustomerData findCustomerDataByCustomerId(Integer customerId){
        Query query = JPA.em().createQuery("select c from CustomerData c where c.customerId =:customerId");
        query.setParameter("customerId", customerId);
        List resultList = query.getResultList();
        if (resultList ==null ||resultList.isEmpty()) {
            throw new EntityNotFoundException("Customer with "
                    + customerId + " not found.");
        }
        if (resultList.size() != 1) {
            throw new InvalidStateException("Found more than one user");
        }
        CustomerData customerData = (CustomerData) resultList.get(0);
        return customerData ;
    }

}
