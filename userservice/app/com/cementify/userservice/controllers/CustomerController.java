package com.cementify.userservice.controllers;

import com.cementify.userservice.exceptions.EntityConflictException;
import com.cementify.userservice.exceptions.EntityNotFoundException;
import com.cementify.userservice.exceptions.InvalidRequestException;
import com.cementify.userservice.exceptions.NotAuthenticatedException;
import com.cementify.userservice.models.Customer;
import com.cementify.userservice.models.CustomerDevice;
import com.cementify.userservice.models.mapping.CustomerMapping;
import com.cementify.userservice.models.request.CustomerDataRequest;
import com.cementify.userservice.models.request.CustomerRequest;
import com.cementify.userservice.models.request.CustomerResetPasswordRequest;
import com.cementify.userservice.models.response.CustomerResponse;
import com.cementify.userservice.services.CustomerService;
import com.google.inject.Inject;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

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

}
