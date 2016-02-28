package com.cementify.userservice.services;

import com.cementify.userservice.exceptions.EntityConflictException;
import com.cementify.userservice.exceptions.EntityNotFoundException;
import com.cementify.userservice.exceptions.InvalidStateException;
import com.cementify.userservice.models.Customer;
import com.cementify.userservice.models.CustomerDevices;
import com.cementify.userservice.models.mapping.CustomerMapping;
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
public class CustomerServiceImp  implements CustomerService{



    @Override
    public CustomerDevices create(CustomerRequest customerRequest) {

        CustomerDevices customerDevices=createAccount(customerRequest);
        sendWelcomeMail(customerRequest);
        return customerDevices;
    }

    @Override
    public Customer createCustomer(CustomerRequest customerRequest) throws EntityConflictException{
        Customer customer=CustomerMapping.getCustomerFromRequest(customerRequest);
        try{
            hashAndSavePassword(customer);
        }catch (PersistenceException e){
            throw new EntityConflictException("Mobile no is already associated with other account");
        }
        return customer;
    }

    @Override
    public CustomerDevices createAccount(CustomerRequest customerRequest) {
        Customer customer=createCustomer(customerRequest);
        UUID ruid=UUID.randomUUID();
        CustomerDevices customerDevices=new CustomerDevices();
        customerDevices.setCustomer(customer);
        customerDevices.setDeviceId(customerRequest.getDeviceId());
        customerDevices.setOsType(customerRequest.getOsType());
        customerDevices.setNotificationId(customerRequest.getNotificationId());
        customerDevices.setRuid(ruid.toString());
        JPA.em().persist(customerDevices);
        return customerDevices;
    }

    @Override
    public CustomerDevices createRuid(CustomerRequest customerRequest) {
        Customer customer =findByMobile(customerRequest.getMobile());
        if (customer == null) {
            throw new EntityNotFoundException("Customer with "
                    + customer.getMobile() + " not found.");
        }
        UUID ruid=UUID.randomUUID();
        CustomerDevices customerDevices=new CustomerDevices();
        customerDevices.setCustomer(customer);
        customerDevices.setDeviceId(customerRequest.getDeviceId());
        customerDevices.setOsType(customerRequest.getOsType());
        customerDevices.setNotificationId(customerRequest.getNotificationId());
        customerDevices.setRuid(ruid.toString());
        JPA.em().persist(customerDevices);
        return customerDevices;
    }

    @Override
    public Customer update(Integer customerId,Customer customer) {
        Customer toUpdate = findCustomerByCustomerId(customerId);
        if (toUpdate == null) {
            throw new EntityNotFoundException("Customer with id "
                    + customer.getCustomerId() + " not found.");
        }

        customer.setCustomerId(customerId);
       // validateVendor(vendor, Update.class);

        toUpdate.setEmail(customer.getEmail());
        toUpdate.setFbEmail(customer.getFbEmail());
        toUpdate.setFbId(customer.getFbId());

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
    public boolean updateFbId(String mobile, String fbId, String fbEmail) {
        Customer customer = findByMobile(mobile);
        customer.setFbId(fbId);
        customer.setFbEmail(fbEmail);
        JPA.em().persist(customer);
        return true;
    }

    @Override
    public boolean updateGoogId(String mobile,String email, String googId) {
        Customer customer = findByMobile(mobile);
        customer.setGoogleEmail(email);
        customer.setGoogleId(googId);
        JPA.em().persist(customer);
        return true;
    }

    @Override
    public boolean setVerified(String mobile) {
        Customer customer = findByMobile(mobile);
        boolean wasVerified = customer.getIsVerified();
        if(wasVerified)
            return  wasVerified;
        customer.setIsVerified(true);
        JPA.em().persist(customer);
        return wasVerified;

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
        Customer customer=(Customer) resultList.get(0);
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

    /*@Override
    public Customer findByRuid(String ruid) {
        Query query = JPA.em()
                .createQuery("select c from Customer c where c.ruid= :ruid");
        query.setParameter("ruid", ruid);
        return executeQuery(query);
    }
*/
    @Override
    public Customer findCustomerByCustomerId(int customerId) {
        return JPA.em().find(Customer.class, customerId);
    }
/*
    @Override
    public  findFacebookUser(String fbId, String mobile) {
        Query query = JPA.em()
                .createQuery("select c from Customer c where c.customerId= :customerId");
        query.setParameter("customerId", customerId);
        return executeQuery(query);
        return null;
    }

    @Override
    public Vendor findGoogleUser(String googId, String email) {
        // check if googleid matched and email or mobile doesn't
        //
        return null;
    }
    */
/*
    @Override
    public Response authenticate(Vendor vendor) {
        if (StringUtils.isEmpty(vendor.getMobile())
                || StringUtils.isEmpty(vendor.getPassword())) {
            //logger.error("Mobile # and password are mandatory for authentication");
            throw new InvalidRequestException(
                    "Mobile # and password are mandatory for authentication.");
        }
        Response response = new Response();

        Vendor savedVendor = findByMobile(vendor.getMobile());
        if (savedVendor == null) {
            response.setStatus(Status.Failure);
            response.setStatus_description("No vendor with mobile # "
                    + vendor.getMobile());
        } else if (!savedVendor.isJoulroadUser()) {
            response.setStatus(Status.Failure);
            response.setStatus_description("Either encrypted password or salt empty "
                    + vendor.getMobile());
        } else if (savedVendor.hasPassword(vendor.getPassword())) {
            response.setStatus(Status.Success);
        } else {
            response.setStatus(Status.Failure);
            response.setStatus_description("Reason unknown "
                    + vendor.getMobile());
        }
        return response;
    }
*/

/*
    @Transactional
    public UserAddressResponse add_address(UserAddressRequest userAddressRequest) {
        VendorAddress address = new VendorAddress();

        address.setVendorId(userAddressRequest.getVendorId());
        address.setFirstName(userAddressRequest.getFirstName());
        address.setLastName(userAddressRequest.getLastName());
        address.setAddressLine1(userAddressRequest.getAddressLine1());
        address.setAddressLine2(userAddressRequest.getAddressLine2());
        address.setCity(userAddressRequest.getCity());
        address.setState(userAddressRequest.getState());
        address.setCountry(userAddressRequest.getCountry());
        address.setPincode(userAddressRequest.getPincode());

        VendorAddress temp = em.merge(address);
        UserAddressResponse response = new UserAddressResponse();
        response.setId(temp.getId());
        return response;
    }

    @Transactional
    public UserAddressResponse update_address(
            UserAddressRequest userAddressRequest) {
        VendorAddress address = new VendorAddress();

        if (userAddressRequest.getId() != null
                && userAddressRequest.getId() > 0) {
            address = em.find(VendorAddress.class, userAddressRequest.getId());
            if (address != null) {
                if (userAddressRequest.getFirstName() != null) {
                    address.setFirstName(userAddressRequest.getFirstName());
                }
                if (userAddressRequest.getLastName() != null) {
                    address.setLastName(userAddressRequest.getLastName());
                }
                if (userAddressRequest.getAddressLine1() != null) {
                    address.setAddressLine1(userAddressRequest
                            .getAddressLine1());
                }
                if (userAddressRequest.getAddressLine2() != null) {
                    address.setAddressLine2(userAddressRequest
                            .getAddressLine2());
                }
                if (userAddressRequest.getCity() != null) {
                    address.setCity(userAddressRequest.getCity());
                }
                if (userAddressRequest.getState() != null) {
                    address.setState(userAddressRequest.getState());
                }
                if (userAddressRequest.getCountry() != null) {
                    address.setCountry(userAddressRequest.getCountry());
                }
                if (userAddressRequest.getPincode() != null) {
                    address.setPincode(userAddressRequest.getPincode());
                }

                VendorAddress temp = em.merge(address);
                UserAddressResponse response = new UserAddressResponse();
                response.setId(temp.getId());
                return response;
            } else {
                throw new EntityNotFoundException("address with id "
                        + userAddressRequest.getId() + " not found.");
            }
        } else {
            throw new EntityNotFoundException("address with id "
                    + userAddressRequest.getId() + " not found.");
        }

    }

    @Transactional
    public UserAddressResponse remove_address(Integer address_id) {

        VendorAddress address = new VendorAddress();
        address.setId(address_id);
        em.remove(em.contains(address) ? address : em.merge(address));
        UserAddressResponse response = new UserAddressResponse();
        response.setId(address_id);
        return response;
    }

    public List<VendorAddress> get_address(Integer vendor_id) {
        TypedQuery<VendorAddress> query = em.createQuery(
                "select u from VendorAddress u where u.vendorId=:vendor_id ",
                VendorAddress.class);
        query.setParameter("vendor_id", vendor_id);

        try {
            List<VendorAddress> vendorAddresses = query.getResultList();
            return vendorAddresses;
        } catch (NoResultException exp) {
            logger.info("No address for user : " + vendor_id);
            throw new EntityNotFoundException("No address for user : "
                    + vendor_id);
        }
    }



    @Transactional
    public void updateNotifForVendor(Integer vendorId) {

        Vendor vendor = findBy(vendorId);
        vendor.setIsNotified(true);
        Vendor temp = em.merge(vendor);

    }

    @Override
    public void saveLocation(UserLocation userLocation) {
        UserLocation userL = findLocationByVendorId(userLocation.getVendorId());
        if (userL == null) {
            UserLocation temp = em.merge(userLocation);
        } else {
            if (userLocation.getLatitude() != null) {
                userL.setLatitude(userLocation.getLatitude());
            }
            if (userLocation.getLongitude() != null) {
                userL.setLongitude(userLocation.getLongitude());
            }
            if (userLocation.getLocation() != null) {
                userL.setLocation(userLocation.getLocation());
            }if(userLocation.getCity()!=null){
                userL.setCity(userLocation.getCity());
            }if(userLocation.getState()!=null){
                userL.setState(userLocation.getState());
            }if(userLocation.getCountry()!=null){
                userL.setCountry(userLocation.getCountry());
            }if(userLocation.getPincode()!=null){
                userL.setPincode(userLocation.getPincode());
            }if(userLocation.getAddress()!=null){
                userL.setAddress(userLocation.getAddress());
            }
            UserLocation temp = em.merge(userL);
        }
    }

    @Override
    public void saveDevice(VendorDeviceMapping vendorDeviceMapping) {
        //VendorDeviceMapping temp = em.merge(vendorDeviceMapping);
        if(!checkIfDuplicate(vendorDeviceMapping)) {
            VendorDeviceMapping temp = em.merge(vendorDeviceMapping);
        } else {
			if (vendorDeviceMapping.getDeviceId() != null) {
				vdm.setDeviceId(vendorDeviceMapping.getDeviceId());
			}
			if (vendorDeviceMapping.getVendorId() != null) {
				vdm.setVendorId(vendorDeviceMapping.getVendorId());
			}
			VendorDeviceMapping temp = em.merge(vdm);
		}
    }

    private boolean checkIfDuplicate(VendorDeviceMapping vendorDeviceMapping) {
        Query query = em
                .createQuery("select c from VendorDeviceMapping c where c.ruid=:ruid and c.vendorId=:vendorId and c.deviceId=:deviceId");
        query.setParameter("ruid", vendorDeviceMapping.getRuid());
        query.setParameter("deviceId", vendorDeviceMapping.getDeviceId());
        query.setParameter("vendorId", vendorDeviceMapping.getVendorId());
        List resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void saveEmails(UserEmails userEmails) {
        UserEmails userE = findEmailsByVendorId(userEmails.getVendorId());
        if (userE == null) {
            UserEmails temp = em.merge(userEmails);
        } else {
            if (userEmails.getEmail() != null) {
                userE.setEmail(userEmails.getEmail());
            }
            UserEmails temp = em.merge(userE);
        }
    }

    private UserEmails findEmailsByVendorId(Integer vendorId) {
        Query query = em
                .createQuery("select c from UserEmails c where c.vendorId= :vendorId");
        query.setParameter("vendorId", vendorId);
        List resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return null;
        }
        if (resultList.size() != 1) {
            throw new InvalidStateException("Found more than one user");
        }
        return (UserEmails) resultList.get(0);
    }

    private UserLocation findLocationByVendorId(Integer vendorId) {
        Query query = em
                .createQuery("select c from UserLocation c where c.vendorId= :vendorId");
        query.setParameter("vendorId", vendorId);
        List resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return null;
        }
        if (resultList.size() != 1) {
            throw new InvalidStateException("Found more than one user");
        }
        return (UserLocation) resultList.get(0);
    }
*/
    /*private void validateCustomer(Customer customer, Class group) {
        if (!constraintViolations.isEmpty()) {
            //logger.error(constraintViolations);
            throw InvalidRequestException
                    .fromConstraintViolations(constraintViolations);
        }

        List<String> errors = customer.validate(group);
        if (!errors.isEmpty()) {
            //logger.error(errors);
            throw new InvalidRequestException(errors);
        }
        Customer found = findByMobile(customer.getMobile());

        if (group.equals(Update.class) && found != null
                && !found.getCustomerId().equals(customer.getCustomerId())) {
                */
           /* logger.error("Phone Number conflicts while updating vendor with id "
                    + vendor.getId()
                    + " to contact detail "
                    + vendor.getMobile());
                    */
    /*
            throw new EntityConflictException(
                    "Phone Number conflicts while updating customer with id "
                            + customer.getCustomerId() + " to contact detail "
                            + customer.getMobile());
        }

        if (group.equals(Create.class) && found != null) {
        */
            /*logger.error("Phone Number conflicts while creating vendor with contact detail "
                    + vendor.getMobile());
                    */
    /*
            throw new EntityConflictException(
                    "Phone Number conflicts while creating customer with contact detail "
                            + customer.getMobile());
        }
    }
    */

}
