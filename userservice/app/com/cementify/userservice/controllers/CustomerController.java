package com.cementify.userservice.controllers;

import com.cementify.userservice.exceptions.EntityConflictException;
import com.cementify.userservice.exceptions.EntityNotFoundException;
import com.cementify.userservice.exceptions.InvalidRequestException;
import com.cementify.userservice.exceptions.NotAuthenticatedException;
import com.cementify.userservice.models.*;
import com.cementify.userservice.models.mapping.CustomerMapping;
import com.cementify.userservice.models.request.CustomerDataRequest;
import com.cementify.userservice.models.request.CustomerRequest;
import com.cementify.userservice.models.request.CustomerResetPasswordRequest;
import com.cementify.userservice.models.response.CustomerResponse;
import com.cementify.userservice.services.CustomerService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roshan on 31/01/16.
 */
public class CustomerController extends Controller {

    @Inject
    CustomerService customerService;
	@Inject
	FormFactory formFactory;

    public Result ping() {
        return ok("Hello UserService");
    }

	@Transactional(value = "customerdb")
    public Result findByMobile(String mobile) {
        //logger.info("Received get vendor with mobile # " + mobile);
        Customer customer = customerService.findByMobile(mobile);
        if (customer == null) {
            return notFound();
        }
        CustomerResponse customerResponse = CustomerMapping
                .getCustomerResponse(customer);
        return ok(Json.toJson(customerResponse));
    }

	@Transactional(value = "customerdb")
	public Result findCustomerByCustomerId(Integer customerId) {
		Customer customer = customerService.findCustomerByCustomerId(customerId);
		if (customer == null) {
			return notFound();
		}
		CustomerResponse customerResponse = CustomerMapping
				.getCustomerResponse(customer);
		return ok(Json.toJson(customerResponse));
	}

	@Transactional(value = "customerdb")
	public Result findCustomerByEmail(String email) {
		Customer customer = customerService.findByEmail(email);
		if (customer == null) {
			return notFound();
		}
		CustomerResponse customerResponse = CustomerMapping
				.getCustomerResponse(customer);
		return ok(Json.toJson(customerResponse));
	}

	@Transactional(value = "customerdb")
	public Result findCustomerByFbId(String fbId) {
		Customer customer = customerService.findByFbId(fbId);
		if (customer == null) {
			return notFound();
		}
		CustomerResponse customerResponse = CustomerMapping
				.getCustomerResponse(customer);
		return ok(Json.toJson(customerResponse));
	}

	@Transactional(value = "customerdb")
	public Result updatePassword() {
		Form<CustomerResetPasswordRequest> customerResetPasswordRequestForm = formFactory.form(CustomerResetPasswordRequest.class);
		customerResetPasswordRequestForm=customerResetPasswordRequestForm.bindFromRequest();
		if (customerResetPasswordRequestForm.hasErrors()) {
			return status(400, "Bad request");
		}
		CustomerResetPasswordRequest customerResetPasswordRequest=customerResetPasswordRequestForm.get();
		try {
			boolean isSucessfullyUpdated= customerService.updatePassword(customerResetPasswordRequest);
			if(isSucessfullyUpdated)
				return status(200,"Password Successfully Updated");
			else
				return status(401,"Not Verified");
		} catch (InvalidRequestException e) {
			return status(400, "Bad request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}
	}


	@Transactional(value = "customerdb")
    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
		Form<CustomerDataRequest> customerDataRequestForm = formFactory.form(CustomerDataRequest.class);
		customerDataRequestForm=customerDataRequestForm.bindFromRequest();
		if (customerDataRequestForm.hasErrors()) {
			return status(400, "Bad request");
		}
        CustomerDataRequest customerDataRequest=customerDataRequestForm.get();
        try {
            CustomerDevice customerDevice = customerService.create(customerDataRequest);
            CustomerResponse customerResponse = CustomerMapping
                    .getCustomerResponseFromCustomerDevice(customerDevice);
            return ok(Json.toJson(customerResponse));
        } catch (InvalidRequestException e) {
            return status(400, "Bad request");
        } catch (EntityConflictException e) {
            return status(409, "Resource conflict");
        }
    }

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result login() {
		Form<CustomerDataRequest> customerDataRequestForm = formFactory.form(CustomerDataRequest.class);
		customerDataRequestForm=customerDataRequestForm.bindFromRequest();
		if (customerDataRequestForm.hasErrors()) {
			return status(400, "Bad request");
		}
		CustomerDataRequest customerDataRequest=customerDataRequestForm.get();
		try {
			CustomerDevice customerDevice = customerService.createRuid(customerDataRequest);
			CustomerResponse customerResponse = CustomerMapping
					.getCustomerResponseFromCustomerDevice(customerDevice);
			return ok(Json.toJson(customerResponse));
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}catch (NotAuthenticatedException e){
			return status(401, "Not Verified");
		}

	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result updateFbId() {
		Form<CustomerRequest> customerRequestForm = formFactory.form(CustomerRequest.class);
		customerRequestForm=customerRequestForm.bindFromRequest();
		if (customerRequestForm.hasErrors()) {
			return status(400, "Bad Request");
		}
		CustomerRequest customerRequest=customerRequestForm.get();
		try {
			customerService.updateFbId(customerRequest);
			return status(200, "FbId Successfully Updated");
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}
	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result updateGoogleId() {
		Form<CustomerRequest> customerRequestForm = formFactory.form(CustomerRequest.class);
		customerRequestForm=customerRequestForm.bindFromRequest();
		if (customerRequestForm.hasErrors()) {
			return status(400, "Bad Request");
		}
		CustomerRequest customerRequest=customerRequestForm.get();
		try {
			customerService.updateGoogId(customerRequest);
			return status(200, "GoogleId Successfully Updated");
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}
	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result setVerified() {
		Form<CustomerRequest> customerRequestForm = formFactory.form(CustomerRequest.class);
		customerRequestForm=customerRequestForm.bindFromRequest();
		if (customerRequestForm.hasErrors()) {
			return status(400, "Bad Request");
		}
		CustomerRequest customerRequest=customerRequestForm.get();
		try{
			customerService.setVerified(customerRequest);
			return status(200, "Customer Verified");
		}catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result updateCustomerData() {
		Form<CustomerDataRequest> customerDataUpdateRequestForm = formFactory.form(CustomerDataRequest.class);
		customerDataUpdateRequestForm=customerDataUpdateRequestForm.bindFromRequest();
		if (customerDataUpdateRequestForm.hasErrors()) {
			return status(400, "Bad Request");
		}
		CustomerDataRequest customerDataRequest =customerDataUpdateRequestForm.get();
		try {
			Customer customer=customerService.update(customerDataRequest);
			CustomerResponse customerResponse = CustomerMapping
					.getCustomerResponse(customer);
			return ok(Json.toJson(customerResponse));
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}
	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result logout() {
		Form<CustomerRequest> customerRequestForm = formFactory.form(CustomerRequest.class);
		customerRequestForm=customerRequestForm.bindFromRequest();
		if (customerRequestForm.hasErrors()) {
			return status(400, "Bad request");
		}
		CustomerRequest customerRequest=customerRequestForm.get();
		try {
			customerService.removeRuid(customerRequest);
			return status(200, "Successfully Logout");
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}catch (NotAuthenticatedException e){
			return status(401, "Not Verified");
		}

	}

	@Transactional(value = "customerdb")
	public Result findCustomerByRuid(String ruid) {
		try {
			CustomerDevice customerDevice = customerService.findByRuid(ruid);
			if (customerDevice == null) {
				return notFound();
			}
			CustomerResponse customerResponse = CustomerMapping
					.getCustomerResponseFromCustomerDevice(customerDevice);
			return ok(Json.toJson(customerResponse));
		}catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	public Result getCustomerAddress(Integer customerId) {
		try {
			List<CustomerAddress> customerAddresses= customerService.findAddressesByCustomerId(customerId);
			return ok(Json.toJson(customerAddresses));
		}catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	public Result getCustomerLocations(Integer customerId) {
		try {
			List<CustomerLocation> customerLocationList= customerService.findLocationsByCustomerId(customerId);
			return ok(Json.toJson(customerLocationList));
		}catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	public Result getCustomerContacts(Integer customerId) {
		try {
			List<CustomerContact> customerContactList= customerService.findContactsByCustomerId(customerId);
			return ok(Json.toJson(customerContactList));
		}catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	public Result getCustomerEmails(Integer customerId) {
		try {
			List<CustomerEmail> customerEmailList= customerService.findEmailsByCustomerId(customerId);
			return ok(Json.toJson(customerEmailList));
		}catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result addCustomerEmail() {
		Form<CustomerEmail> customerEmailForm = formFactory.form(CustomerEmail.class);
		customerEmailForm=customerEmailForm.bindFromRequest();
		if (customerEmailForm.hasErrors()) {
			return status(400, "Bad Request");
		}
		CustomerEmail customerEmail=customerEmailForm.get();
		try {
			customerService.addEmail(customerEmail);
			return status(200, "Successfully Added");
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result addCustomerAddressList() {
		try {
			JsonNode jsonNode=request().body().asJson();
			Test test=Json.fromJson(jsonNode,Test.class);
			List<CustomerAddress> customerAddresses=test.getCustomerAddresses();
			for(CustomerAddress customerAddress :customerAddresses){
				Form<CustomerAddress> customerAddressForm = formFactory.form(CustomerAddress.class);
				customerAddressForm=customerAddressForm.fill(customerAddress);
				if (customerAddressForm.hasErrors()) {
					return status(400, "Bad Request");
				}
				CustomerAddress customerAdd=customerAddressForm.get();
				customerService.addAddress(customerAdd);
			}

			return status(200, "Successfully Added");
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}catch (Exception e){
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result addCustomerLocationList() {
		Form<CustomerLocation> customerLocationForm = formFactory.form(CustomerLocation.class);
		customerLocationForm=customerLocationForm.bindFromRequest();
		if (customerLocationForm.hasErrors()) {
			return status(400, "Bad Request");
		}
		CustomerLocation customerLocation=customerLocationForm.get();
		try {
			customerService.addLocation(customerLocation);
			return status(200, "Successfully Added");
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result addCustomerContactList() {
		Form<CustomerContact> customerContactForm = formFactory.form(CustomerContact.class);
		customerContactForm=customerContactForm.bindFromRequest();
		if (customerContactForm.hasErrors()) {
			return status(400, "Bad Request");
		}
		CustomerContact customerContact=customerContactForm.get();
		try {
			customerService.addContact(customerContact);
			return status(200, "Successfully Added");
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result addCustomerEmailList() {
		Form<CustomerEmail> customerEmailForm = formFactory.form(CustomerEmail.class);
		customerEmailForm=customerEmailForm.bindFromRequest();
		if (customerEmailForm.hasErrors()) {
			return status(400, "Bad Request");
		}
		CustomerEmail customerEmail=customerEmailForm.get();
		try {
			customerService.addEmail(customerEmail);
			return status(200, "Successfully Added");
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result addCustomerAddress() {
		Form<CustomerAddress> customerAddressForm = formFactory.form(CustomerAddress.class);
		customerAddressForm=customerAddressForm.bindFromRequest();
		if (customerAddressForm.hasErrors()) {
			return status(400, "Bad Request");
		}
		CustomerAddress customerAddress=customerAddressForm.get();
		try {
			customerService.addAddress(customerAddress);
			return status(200, "Successfully Added");
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result addCustomerLocation() {
		Form<CustomerLocation> customerLocationForm = formFactory.form(CustomerLocation.class);
		customerLocationForm=customerLocationForm.bindFromRequest();
		if (customerLocationForm.hasErrors()) {
			return status(400, "Bad Request");
		}
		CustomerLocation customerLocation=customerLocationForm.get();
		try {
			customerService.addLocation(customerLocation);
			return status(200, "Successfully Added");
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}

	@Transactional(value = "customerdb")
	@BodyParser.Of(BodyParser.Json.class)
	public Result addCustomerContact() {
		Form<CustomerContact> customerContactForm = formFactory.form(CustomerContact.class);
		customerContactForm=customerContactForm.bindFromRequest();
		if (customerContactForm.hasErrors()) {
			return status(400, "Bad Request");
		}
		CustomerContact customerContact=customerContactForm.get();
		try {
			customerService.addContact(customerContact);
			return status(200, "Successfully Added");
		} catch (InvalidRequestException e) {
			return status(400, "Bad Request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}

	}


}
