package com.cementify.userservice.services;

import com.cementify.userservice.exceptions.EntityConflictException;
import com.cementify.userservice.exceptions.EntityNotFoundException;
import com.cementify.userservice.exceptions.InvalidStateException;
import com.cementify.userservice.exceptions.NotAuthenticatedException;
import com.cementify.userservice.models.Customer;
import com.cementify.userservice.models.CustomerDevice;
import com.cementify.userservice.models.mapping.CustomerMapping;
import com.cementify.userservice.models.request.CustomerDataRequest;
import com.cementify.userservice.models.request.CustomerRequest;
import com.cementify.userservice.models.request.CustomerResetPasswordRequest;
import play.db.jpa.JPA;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

/**
 * Created by roshan on 30/01/16.
 */
public class CustomerServiceImp implements CustomerService {


    @Override
    public CustomerDevice create(CustomerDataRequest customerRequest) {

        CustomerDevice customerDevice = createAccount(customerRequest);
        sendWelcomeMail(customerRequest);
        return customerDevice;
    }

    @Override
    public Customer createCustomer(CustomerRequest customerRequest) throws EntityConflictException {
        Customer customer = CustomerMapping.getCustomerFromRequest(customerRequest);
        try {
            hashAndSavePassword(customer);
        } catch (PersistenceException e) {
            throw new EntityConflictException("Mobile no is already associated with other account");
        }
        return customer;
    }

    @Override
    public CustomerDevice createAccount(CustomerDataRequest customerRequest) {
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
    public CustomerDevice createRuid(CustomerDataRequest customerRequest) {
        Customer customer = findByMobile(customerRequest.getMobile());
        if (customer == null) {
            throw new EntityNotFoundException("Customer with "
                    + customer.getMobile() + " not found.");
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
    public Customer update(CustomerDataRequest customerRequest) {
        Customer toUpdate = findCustomerByCustomerId(customerRequest.getCustomerId());
        if (toUpdate == null) {
            throw new EntityNotFoundException("Customer with id "
                    + customerRequest.getCustomerId() + " not found.");
        }
        toUpdate.setEmail(customerRequest.getEmail());
        toUpdate.setFbEmail(customerRequest.getFbEmail());
        toUpdate.setFbId(customerRequest.getFbId());
        toUpdate.setBirthday(customerRequest.getBirthDay());
        toUpdate.setGoogleEmail(customerRequest.getGoogleEmail());
        toUpdate.setGoogleId(customerRequest.getGoogleId());
        toUpdate.setFirstName(customerRequest.getFirstName());
        toUpdate.setLastName(customerRequest.getLastName());
        toUpdate.setIsMale(customerRequest.getIsMale());
        toUpdate.setIsVerified(customerRequest.getIsVerified());
        toUpdate.setIsCodBlocked(customerRequest.getIsCodBlocked());
        toUpdate.setIsPrepaidBlocked(customerRequest.getIsPrepaidBlocked());
        JPA.em().persist(toUpdate);
        return toUpdate;
    }

    @Override
    public boolean updatePassword(CustomerResetPasswordRequest customerResetPasswordRequest) {
        Customer customer = findByMobile(customerResetPasswordRequest.getMobile());
        if (customer == null) {
            throw new EntityNotFoundException("Customer with mobile #  " + customerResetPasswordRequest.getMobile()
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
        Customer customer = findByMobile(customerRequest.getMobile());
        if (customer == null) {
            throw new EntityNotFoundException("Customer with "
                    + customer.getMobile() + " not found.");
        }
        customer.setFbId(customerRequest.getFbId());
        customer.setFbEmail(customerRequest.getFbEmail());
        JPA.em().persist(customer);
    }

    @Override
    public void updateGoogId(CustomerRequest customerRequest) {
        Customer customer = findByMobile(customerRequest.getMobile());
        if (customer == null) {
            throw new EntityNotFoundException("Customer with "
                    + customer.getMobile() + " not found.");
        }
        customer.setGoogleEmail(customerRequest.getGoogleEmail());
        customer.setGoogleId(customerRequest.getGoogleId());
        JPA.em().persist(customer);
    }

    @Override
    public void setVerified(CustomerRequest customerRequest) {
        Customer customer = findByMobile(customerRequest.getMobile());
        if (customer == null) {
            throw new EntityNotFoundException("Customer with "
                    + customer.getMobile() + " not found.");
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

        if (resultList.isEmpty()) {
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
    public void removeRuid(CustomerRequest customerRequest) {
        CustomerDevice customerDevice=findByRuid(customerRequest.getRuid());
        if(customerRequest.getCustomerId()==customerDevice.getCustomer().getCustomerId()){
           JPA.em().remove(customerDevice);
        }else {
            throw new NotAuthenticatedException("CustomerId conflict Exception");
        }
    }

}
