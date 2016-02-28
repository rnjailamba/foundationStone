package com.cementify.userservice.controllers;

import com.cementify.userservice.exceptions.EntityConflictException;
import com.cementify.userservice.exceptions.EntityNotFoundException;
import com.cementify.userservice.exceptions.InvalidRequestException;
import com.cementify.userservice.models.Customer;
import com.cementify.userservice.models.CustomerDevices;
import com.cementify.userservice.models.mapping.CustomerMapping;
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
		Form<CustomerRequest> customerRequestForm = formFactory.form(CustomerRequest.class);
		customerRequestForm=customerRequestForm.bindFromRequest();
		if (customerRequestForm.hasErrors()) {
			return status(400, "Bad request");
		}
        CustomerRequest customerRequest=customerRequestForm.get();
        try {
            CustomerDevices customerDevices= customerService.create(customerRequest);
            CustomerResponse customerResponse = CustomerMapping
                    .getCustomerResponseFromCustomerDevice(customerDevices);
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
		Form<CustomerRequest> customerRequestForm = formFactory.form(CustomerRequest.class);
		customerRequestForm=customerRequestForm.bindFromRequest();
		if (customerRequestForm.hasErrors()) {
			return status(400, "Bad request");
		}
		CustomerRequest customerRequest=customerRequestForm.get();
		try {
			CustomerDevices customerDevices= customerService.createRuid(customerRequest);
			CustomerResponse customerResponse = CustomerMapping
					.getCustomerResponseFromCustomerDevice(customerDevices);
			return ok(Json.toJson(customerResponse));
		} catch (InvalidRequestException e) {
			return status(400, "Bad request");
		} catch (EntityNotFoundException e) {
			return status(404, e.getMessage());
		}
	}
    /*
    package com.limeroad.services.user.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.limeroad.services.user.controller.exception.BadRequestException;
import com.limeroad.services.user.controller.exception.ResourceConflictException;
import com.limeroad.services.user.controller.exception.ResourceNotFoundException;
import com.limeroad.services.user.controller.exception.UnauthorizedException;
import com.limeroad.services.user.mapping.VendorMapping;
import com.limeroad.services.user.model.AdminPortalBrand;
import com.limeroad.services.user.model.AdminPortalVendor;
import com.limeroad.services.user.model.AdminProperties;
import com.limeroad.services.user.model.Response;
import com.limeroad.services.user.model.Response.Status;
import com.limeroad.services.user.model.Shop;
import com.limeroad.services.user.model.UserEmails;
import com.limeroad.services.user.model.UserLocation;
import com.limeroad.services.user.model.Vendor;
import com.limeroad.services.user.model.VendorAddress;
import com.limeroad.services.user.model.VendorDeviceMapping;
import com.limeroad.services.user.model.request.UserAddressRequest;
import com.limeroad.services.user.model.request.VendorContactsRequest;
import com.limeroad.services.user.model.request.VendorRequest;
import com.limeroad.services.user.model.response.UpdatePasswordResponse;
import com.limeroad.services.user.model.response.UserAddressResponse;
import com.limeroad.services.user.model.response.VendorContactsResponse;
import com.limeroad.services.user.model.response.VendorCreateResponse;
import com.limeroad.services.user.model.response.VendorResponse;
import com.limeroad.services.user.model.response.VendorSearchResponse;
import com.limeroad.services.user.model.response.verificationResponse;
import com.limeroad.services.user.model.response.verificationResponse.VerificationStatus;
import com.limeroad.services.user.service.ProductService;
import com.limeroad.services.user.service.VendorService;
import com.limeroad.services.user.service.exception.EntityConflictException;
import com.limeroad.services.user.service.exception.EntityNotFoundException;
import com.limeroad.services.user.service.exception.InvalidRequestException;
import com.limeroad.services.user.service.validation.Create;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;

@Controller
public class VendorController {
	@Autowired
	private VendorService vendorService;
	@Autowired
	private ProductService productService;

	private static final Logger logger = Logger
			.getLogger(VendorController.class);
	private static final String defaultCity = "delhi";
	private String vendorModel="3";
	private String marginPercent="0";
	private String photoCostOption="4";
	private String photoCost="0";
	private String vendorType="B";
	private String defaultValue = "0";
	private int httpRedirect = 302;
	private String Zero = "0";
	private AsyncHttpClient asyncHttpClient;
	private String dashboardLandingPage = "2";
	private String productListingPage = "6";
	private String limeshopSource = "2";
	private static String productLiveNotificationTextwithMOU = "Congratulations! Your store is live on limeroad %s. Please share and get order to build your seller score.";
	private static String productLiveNotificationTextwithoutMOU =  "Congratulations! Your store is live on limeroad %s. Please share and get order to build your seller score.";


	@RequestMapping(value = "/customers/mobile/{mobile}", method = RequestMethod.GET)
	@ResponseBody
	public VendorResponse findById(@PathVariable String mobile) {
		logger.info("Received get vendor with mobile # " + mobile);
		Vendor vendor = vendorService.findByMobile(mobile);
		if (vendor == null) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList("No vendor with mobile : " + mobile)));
		}
		VendorMapping vendorMapping = new VendorMapping();
		return vendorMapping.getVendorResponse(vendor);
		// return vendor;
	}

	@RequestMapping(value = "/vendor_search/{mobile}", method = RequestMethod.GET)
	@ResponseBody
	public VendorSearchResponse findVendor(@PathVariable String mobile) {
		Vendor vendor = vendorService.findByMobile(mobile);
		if (vendor == null) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList("No created Date with mobile : " + mobile)));
		}
		VendorMapping vendorMapping = new VendorMapping();
		return vendorMapping.getSearchResponse(vendor);
	}

	@RequestMapping(value = "/customers/email/{email}", method = RequestMethod.GET)
	@ResponseBody
	public VendorResponse findByEmail(@PathVariable String email) {
		// assuming to be email for now
		Vendor vendor = vendorService.findByEmail(email);
		if (vendor == null) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList("No vendor with email : " + email)));
		}
		VendorMapping vendorMapping = new VendorMapping();
		return vendorMapping.getVendorResponse(vendor);
	}

	@RequestMapping(value = "/customers/ruid/{ruid}", method = RequestMethod.GET)
	@ResponseBody
	public VendorResponse findByRuid(@PathVariable String ruid) {
		// assuming to be email for now
		Vendor vendor = vendorService.findByRuid(ruid);
		if (vendor == null) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList("No vendor with ruid : " + ruid)));
		}
		VendorMapping vendorMapping = new VendorMapping();
		return vendorMapping.getVendorResponse(vendor);
	}

	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public VendorCreateResponse create(@RequestBody VendorRequest vendorRequest) {
		// System.out.println(customer.toString());
		try {
			VendorMapping vendorMapping = new VendorMapping();

			Vendor vendor = vendorService.create(vendorMapping
					.getVendorFromRequest(vendorRequest));
			VendorCreateResponse response = new VendorCreateResponse();
			response.setVendor_id(vendor.getId());
			response.setMobile(vendor.getMobile());
			response.setRuid(vendor.getRuid());
			return response;
		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));

		}
	}

	@RequestMapping(value = "/user/device", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void saveDevice(@Validated(Create.class) @RequestBody VendorDeviceMapping vendorDeviceMapping) {
		// System.out.println(customer.toString());
		try {
			vendorService.saveDevice(vendorDeviceMapping);
		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));

		}
	}

	@RequestMapping(value = "/user/location", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void saveLocation(@Validated(Create.class) @RequestBody UserLocation userLocation) {
		// System.out.println(customer.toString());
		try {
			vendorService.saveLocation(userLocation);
		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));

		}
	}

	@RequestMapping(value = "/user/emails", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void saveEmails(@Validated(Create.class) @RequestBody UserEmails userEmails) {
		// System.out.println(customer.toString());
		try {
			vendorService.saveEmails(userEmails);
		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));

		}
	}

	@RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public UpdatePasswordResponse updatePassword(
			@RequestParam("mobile") String mobile,
			@RequestParam("password") String password,
			@RequestParam("old_password") String oldPassword) {
		try {
			boolean status = vendorService.updatePassword(mobile, password,
					oldPassword);
			if (!status) {
				throw new UnauthorizedException();
			}
		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		}
		UpdatePasswordResponse upr = new UpdatePasswordResponse();
		upr.setMobile(mobile);
		upr.setVerificationStatus(com.limeroad.services.user.model.response.UpdatePasswordResponse.VerificationStatus.Success);
		return upr;
	}

	@RequestMapping(value = "/setFbParams", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public String setFbParams(@RequestParam("email") String mobile,
			@RequestParam("fbId") String fbId,
			@RequestParam("fbEmail") String fbEmail) {
		try {
			boolean status = vendorService.updateFbId(mobile, fbId, fbEmail);
			if (!status) {
				throw new UnauthorizedException();
			}
		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		}
		return "Done";
	}

	@RequestMapping(value = "/setGoogParams", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public String setGoogParams(@RequestParam("email") String email,
			@RequestParam("googId") String googId) {
		try {
			boolean status = vendorService.updateGoogId(email, googId);
			if (!status) {
				throw new UnauthorizedException();
			}
		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		}
		return "Done";
	}

	@RequestMapping(value = "/setverified", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public verificationResponse setVerified(
			@RequestParam("mobile") String mobile) {
		boolean wasVerified = false;
		verificationResponse vr = new verificationResponse();
		try {
			vr.setMobile(mobile);
			wasVerified = vendorService.setVerified(mobile);
			if (wasVerified) {
				vr.setVerificationStatus(VerificationStatus.NoChange);
			}
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		}
		vr.setVerificationStatus(VerificationStatus.Success);
		return vr;
	}

	@RequestMapping(value = "/customers/authenticate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public VendorResponse authenticate(@RequestBody VendorRequest vendorRequest) {
		try {
			VendorMapping vendorMapping = new VendorMapping();
			Response authenticate = vendorService.authenticate(vendorMapping
					.getVendorFromRequest(vendorRequest));
			if (authenticate.getStatus() == Status.Failure) {
				throw new UnauthorizedException();
			}
			return vendorMapping.getVendorResponse(vendorService
					.findByMobile(vendorRequest.getMobile()));
		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		}
	}

	@RequestMapping(value = "/customers/resetPassword", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String resetPassword(@RequestParam("mobile") String mobile,
			@RequestParam("password") String password) {
		try {
			return Boolean.toString(vendorService.resetPassword(mobile,
					password));

		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		}
	}

	@RequestMapping(value = "/customers/addaddress", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public UserAddressResponse add_address(
			@RequestBody UserAddressRequest userAddressRequest) {
		try {
			return vendorService.add_address(userAddressRequest);

		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		}
	}

	@RequestMapping(value = "/customers/updateaddress", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public UserAddressResponse update_address(
			@RequestBody UserAddressRequest userAddressRequest) {
		try {
			return vendorService.update_address(userAddressRequest);

		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		}
	}

	@RequestMapping(value = "/customers/removeAddress", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public UserAddressResponse remove_address(
			@RequestParam("address_id") Integer address_id) {
		try {
			return vendorService.remove_address(address_id);

		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		}
	}

	@RequestMapping(value = "/customers/getaddresses/{vendor_id}", method = RequestMethod.GET)
	@ResponseBody
	public List<VendorAddress> findAddressByVendorId(
			@PathVariable Integer vendor_id) {
		try {
			return vendorService.get_address(vendor_id);

		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		}

	}

	@RequestMapping(value = "/vendors/store_contacts", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public VendorContactsResponse store_contacts(
			@RequestBody VendorContactsRequest vendorContactsRequest) {
		try {
			return vendorService.store_contacts(vendorContactsRequest);

		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		}
	}

	@RequestMapping(value = "/vendors/sendNotification", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void sendNotification(
			@RequestParam("vendor_ids") JSONObject vendorBrandMap) {
		try {
			logger.info("vendorIds: " + vendorBrandMap);
			StringBuffer sb = new StringBuffer();
			Map<String,String> prodMOUNotifVendors = new HashMap<String, String>();
			Map<String,String> prodNoMOUNotifVendors = new HashMap<String, String>();
			Iterator<String> it = vendorBrandMap.keys();
			while (it.hasNext()) {
				String s = it.next();
				int vendor_id = vendorService
						.getVendorfromAdminPortalMapping(Integer.parseInt(s));
				logger.info("Limeshop VendorId: " + vendor_id);

				if (vendor_id != 0) {
					logger.info("Vendor Object: "
							+ vendorService.findBy(vendor_id));

					if (!vendorService.findBy(vendor_id).getIsNotified()) {
						Shop shop = vendorService.getShop(vendor_id);
						try {
							if (shop != null) {
								String seoURL = (String) vendorBrandMap.get(s);
								int shop_id = shop.getId();
								if (shop.getShopCurrentStatus() == 1) {
									prodMOUNotifVendors.put(
											String.valueOf(vendor_id),seoURL);
								} else {
									prodNoMOUNotifVendors.put(
											String.valueOf(vendor_id),seoURL);
								}
								productService.addShopShareURL(shop_id,seoURL);
							}
						} catch (JSONException e) {
							logger.info("Exception while sending Notification for " + vendor_id,e);
						}
						vendorService.updateNotifForVendor(vendor_id);

					}
				}
			}
			sendNotificationforProductLive(prodMOUNotifVendors, productLiveNotificationTextwithMOU);
			sendNotificationforProductLive(prodNoMOUNotifVendors, productLiveNotificationTextwithoutMOU);

		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));
		}
	}

	private void sendNotificationforProductLive(Map<String,String> vendors,String textTemplate) {
		for(String vendor: vendors.keySet()){
			String brandShareURL = "www.limeroad.com";
			try {
				brandShareURL += vendors.get(vendor);
				String text = String.format(textTemplate,brandShareURL);
				sendProductLiveNotification(vendor,text, productListingPage);
			} catch (Exception e) {
				logger.error("Exception for " +  vendor,e);
			}
		}
	}


	@RequestMapping(value = "/vendor_registration", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@RequestParam("vendor_id") Integer vendor_id,
			@RequestParam("shop_id") Integer shop_id,
			@RequestParam("brand_id") Integer brand_id,
			@RequestParam("shop_name") String shop_name) throws IOException {

		AdminPortalVendor vendor = vendorService.findByVendorId(vendor_id);
		if(vendor != null){
			return;
		}
		Shop shop = vendorService.getShop(vendor_id);
		if(shop==null){
			return;
		}
		String email = shop.getShopEmail();
		String domain = shop.getDomainIdentifier();
		if(StringUtils.isBlank(domain)){
			return;
		}
		if(StringUtils.isBlank(email)){
			email = domain.replaceAll("\\s","")+"@limeshop.in";
		}
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		HttpClient httpClient = null;
		try {
			httpClient = HttpClientBuilder.create().build();
			HttpPost request = loginAdminPortal();
	        HttpResponse response = httpClient.execute(request);
	        String mobile = "";
	        if(response!=null && (response.getStatusLine().getStatusCode()==httpRedirect)){
	        	Vendor limeShopVendor = vendorService.findBy(vendor_id);
	        	if(limeShopVendor!=null){
	        		mobile = limeShopVendor.getMobile();
	        	}
	        	int[] adminPortalIds = createVendorInAdminPortal(httpClient,shop_name, mobile, domain, email);
	        	int admin_portal_vendor_id = adminPortalIds[0];
	        	int admin_portal_warehouse_id = adminPortalIds[1];

				if (admin_portal_vendor_id != 0 && admin_portal_warehouse_id != 0) {
					int adminPortalBrandId = createBrandInAdminPortal(
							httpClient, admin_portal_vendor_id, shop_name);
					vendorService.createVendorMapping(vendor_id,
							admin_portal_vendor_id);
					vendorService.createWarehouseMapping(shop_id,
							admin_portal_warehouse_id);
					vendorService.createBrandMapping(brand_id,
							adminPortalBrandId);
				}
	        }
		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));

		}
		catch(Exception e){
			logger.info("Exception in create vendor registration",e);
		}
		finally {
	    	httpClient.getConnectionManager().shutdown();
	    }
	}

	@RequestMapping(value = "/admin_product_registration", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public int adminProductRegistration(@RequestParam("brand_id") Integer brand_id,
			@RequestParam("product_name") String productName,
			@RequestParam("vsc") String vendorStyleCode,
			@RequestParam("product_description") String productDesc,
			@RequestParam("variants") JSONArray variants,
			@RequestParam("LR_category_id") String LRCategoryId
			) throws IOException {

		if (StringUtils.isBlank(productDesc)){
			productDesc = productName;
		}
		if (StringUtils.isBlank(vendorStyleCode)){
			vendorStyleCode = productName;
		}
		String vendorColor="multi";
		String categoryId = LRCategoryId;

		Vendor v = vendorService.getBrand(brand_id);
		if(v == null ){ // Vendor not created yet
			logger.error("Purani dukhan mein naya samaan");
			throw new BadRequestException(Collections.singletonList("Purani dukhan mein naya samaan"));
		}

		AdminPortalVendor vendor = vendorService.findByVendorId(v.getId());
		AdminPortalBrand brand = vendorService.findByBrandId(brand_id);

		if(vendor == null || brand == null){ // Vendor not created yet
			logger.error("Purani dukhan mein naya samaan");
			throw new BadRequestException(Collections.singletonList("Purani dukhan mein naya samaan"));
		}
		String vendorId=String.valueOf(vendor.getAdminPortalVendorId());
		String brandId=String.valueOf(brand.getAdminPortalBrandId());
		int[] upid = {-1};
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		HttpClient httpClient = null;
		try {
			httpClient = HttpClientBuilder.create().build();
			HttpPost request = loginAdminPortal();
	        HttpResponse response = httpClient.execute(request);
	        if(response!=null && response.getStatusLine().getStatusCode()==302){
	        	upid = createProductInAdminPortal(httpClient, productName, vendorId, brandId, vendorStyleCode,
	        			productDesc, categoryId, vendorColor,variants);
	        }
		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));

		}
		catch(Exception e){
			logger.error("Exception in pushing product to admin portal",e);
		}
		finally {
	    	httpClient.getConnectionManager().shutdown();
	    }
		return upid[0];
	}

	private void sendProductLiveNotification(String limeshopVendorIds,
			String text, String landingPage) {
		asyncHttpClient = new AsyncHttpClient();
		try {
			JSONObject minVersion = new JSONObject();
			minVersion.put("android", "0");
			minVersion.put("ios", "0");
			JSONObject orderNotif = new JSONObject();
			JSONObject notificationText = new JSONObject();
			List<String> emails = new ArrayList<String>();
			emails.add("");
			notificationText.put("Text", text);
			notificationText.put("Title", "Limeroad Seller App");
			orderNotif.put("notificationText", notificationText.toString());
			orderNotif.put("minVersion", minVersion);
			orderNotif.put("notificationType", "-1");
			orderNotif.put("creationTimestamp", new Date().getTime());
			orderNotif.put("receiverEmailIds", emails);
			orderNotif.put("landingPageType", landingPage);
			orderNotif.put("landingPageId", "");
			Request req = new RequestBuilder("POST")
					.setUrl(AdminProperties.notificationUrl)
					.addHeader("Content-Type", "application/json")
					.addParameter("notifications",
							Arrays.asList(orderNotif).toString())
					.addParameter("users", limeshopVendorIds)
					.addParameter("source", limeshopSource ).build();
			asyncHttpClient.prepareRequest(req).execute();
			logger.info(limeshopVendorIds + ":" + orderNotif.toString());
		} catch (IOException e) {
			logger.error("IOException in sending Notification", e);
		} catch (JSONException e) {
			logger.error("JSONException in sending Notification", e);
		}

	}

	@RequestMapping(value = "/admin_product_images", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public int adminProductImgRegistration(@RequestParam("upid") Integer upid,@RequestParam("brand_id") Integer brandId,
			@RequestParam("imgURLs") List<String> imgURLs
			) throws IOException, JSONException {

		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		HttpClient httpClient = null;
		JSONObject imgDataMap=new JSONObject();
		JSONArray imgData = new JSONArray();
		for(String s: imgURLs){
			imgData.put("products/brand_" + brandId + "/" + s);
		}
		imgDataMap.put(String.valueOf(upid),imgData);
		try {
			httpClient = HttpClientBuilder.create().build();
			HttpPost request = loginAdminPortal();
	        HttpResponse response = httpClient.execute(request);
	        if(response!=null && response.getStatusLine().getStatusCode()==302){
	        	HttpPost getrequest = new HttpPost(AdminProperties.imgMigrateUrl);
	    		getrequest.setHeader("Content-Type", "application/json");
	            String data = imgDataMap.toString();
	            HttpEntity entity = new ByteArrayEntity(data.getBytes("UTF-8"));
	    	    getrequest.setEntity(entity);
	    		HttpResponse cityresponse = httpClient.execute(getrequest);

	    		System.out.println("Response Code : "
	    		            + cityresponse.getStatusLine().getStatusCode());
	    		System.out.println(cityresponse.getEntity().getContent());
	    		BufferedReader rd = new BufferedReader(new InputStreamReader(cityresponse.getEntity().getContent()));

	    		StringBuffer result = new StringBuffer();
	    		String line = "";
	    		while ((line = rd.readLine()) != null) {
	    		    result.append(line);
	    		}
	    		logger.info(result);
	        }
		} catch (InvalidRequestException e) {
			throw new BadRequestException(e.getErrorMessages());
		} catch (EntityConflictException e) {
			throw new ResourceConflictException(new ArrayList<String>(
					Arrays.asList(e.getMessage())));

		}
		catch(Exception e){
			logger.error("Exception in migrating images",e);
		}
		finally {
	    	httpClient.getConnectionManager().shutdown();
	    }
		return 1;
	}

	protected int[] createVendorInAdminPortal(HttpClient httpClient, String shopName, String mobile, String domain, String email)
			throws IOException, ClientProtocolException, JSONException {

		HttpPost getrequest = new HttpPost(AdminProperties.vendorAddUrl);
		getrequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		BasicNameValuePair[] params = {
				new BasicNameValuePair("_method", "POST"),
	            new BasicNameValuePair("data[Vendor][companyName]", domain),
	            new BasicNameValuePair("data[Vendor][cpEmail]", email),
	            new BasicNameValuePair("data[Vendor][mobile]", mobile),
	            new BasicNameValuePair("data[Vendor][cpPhone]", mobile),
	            new BasicNameValuePair("data[Vendor][model]", vendorModel),
	            new BasicNameValuePair("data[Vendor][marginPercent]", marginPercent),
	            new BasicNameValuePair("data[Vendor][marginPercentDescription]", ""),
	            new BasicNameValuePair("data[Vendor][photographCostOptions]", photoCostOption),
	            new BasicNameValuePair("data[Vendor][photographCost]", photoCost),
	            new BasicNameValuePair("data[Vendor][type]", vendorType),
	            new BasicNameValuePair("data[Vendor][isVerified]",Zero),
	            new BasicNameValuePair("data[Warehouse][name]", shopName),
	            new BasicNameValuePair("data[Warehouse][return_routing_code]", ""),
	            new BasicNameValuePair("data[Warehouse][po_required]", defaultValue),
	            new BasicNameValuePair("data[Warehouse][ringfenced]", defaultValue),
	            new BasicNameValuePair("data[Warehouse][remarks_sourcing]", ""),
	            new BasicNameValuePair("data[Warehouse][tear_free_small]", defaultValue),
	            new BasicNameValuePair("data[Warehouse][tear_free_medium]", defaultValue),
	            new BasicNameValuePair("data[Warehouse][tear_free_large]", defaultValue),
	            new BasicNameValuePair("data[Warehouse][wrapping_paper]", defaultValue),
	            new BasicNameValuePair("data[Warehouse][tape_roll_packaging]", defaultValue)};
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(Arrays.asList(params),"UTF-8");
	    getrequest.setEntity(urlEncodedFormEntity);
		HttpResponse vendorCreateResponse = httpClient.execute(getrequest);

		logger.info("Response Code : "
		            + vendorCreateResponse.getStatusLine().getStatusCode());
		logger.info(vendorCreateResponse.getEntity().getContent());
		BufferedReader rd = new BufferedReader(new InputStreamReader(vendorCreateResponse.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}
		logger.info("The result when vendor create in admin portal"+result.toString());
		JSONObject obj = new JSONObject(result.toString());
		logger.info("The result returned from admin portal on vendor create"+result );
		int[] adminIds = new int[2];
		try {
			adminIds[0] = obj.getInt("id");
			adminIds[1] = obj.getInt("warehouse_id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			logger.info("Exception in create vendor in admin portal",e);
		}
		return adminIds;
	}


	protected int[] createProductInAdminPortal(HttpClient httpClient, String productName,String vendorId,String brandId,
			String vendorStyleCode,String productDesc,String categoryId,
			String vendorColor,JSONArray variants)
			throws IOException, ClientProtocolException, JSONException {

		HttpPost getrequest = new HttpPost(AdminProperties.productAddUrl);
		getrequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		BasicNameValuePair[] params = {
				new BasicNameValuePair("_method", "POST"),
	            new BasicNameValuePair("uploadType", "new"),
	            new BasicNameValuePair("is_new_product","yes"),
	            new BasicNameValuePair("lrname",productName),
	            new BasicNameValuePair("vendor_id", vendorId),
	            new BasicNameValuePair("brand_id", brandId),
	            new BasicNameValuePair("vendor_style_code", vendorStyleCode),
	            new BasicNameValuePair("lrdescription", productDesc),
	            new BasicNameValuePair("vendor_color", vendorColor),
	            new BasicNameValuePair("city", defaultCity),
	            new BasicNameValuePair("unitOfSet", "each"),
	            new BasicNameValuePair("currency", "INR"),
	            new BasicNameValuePair("amCmsn", "0.0"),
	            new BasicNameValuePair("tax", "10"),
	            new BasicNameValuePair("category_id", categoryId),
	            new BasicNameValuePair("physicalQC", "no"),
	            new BasicNameValuePair("imageAvailable", "no"),
	            new BasicNameValuePair("perishable", "no"),
	            new BasicNameValuePair("variants", variants.toString())

		};
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(Arrays.asList(params),"UTF-8");
	    getrequest.setEntity(urlEncodedFormEntity);
		HttpResponse cityresponse = httpClient.execute(getrequest);

		System.out.println("Response Code : "
		            + cityresponse.getStatusLine().getStatusCode());
		System.out.println(cityresponse.getEntity().getContent());
		BufferedReader rd = new BufferedReader(new InputStreamReader(cityresponse.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}
		logger.info(result);
		JSONObject obj = new JSONObject(result.toString());
		Map<String,String> prodMap = new HashMap<String, String>();
		int[] upid = new int[1];

		try {
			upid[0] = obj.getInt("upid");
		} catch (JSONException e) {
			logger.error("Error in parsing productAPI response",e);
		}
		return upid;
	}

	protected int createBrandInAdminPortal(HttpClient httpClient, int vendor_id, String shopName)
			throws IOException, ClientProtocolException, JSONException {

		HttpPost postRequest = new HttpPost(AdminProperties.brandAddUrl);
		postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		BasicNameValuePair[] params = {
				new BasicNameValuePair("_method", "POST"),
	            new BasicNameValuePair("data[Brand][id]:", ""),
	            new BasicNameValuePair("data[Brand][vendor]", ""+vendor_id),
	            new BasicNameValuePair("data[Brand][name]", shopName),
	            new BasicNameValuePair("data[Brand][description]", shopName),
	            new BasicNameValuePair("data[Brand][type]", defaultValue),
	            new BasicNameValuePair("data[Brand][is_showcased]", defaultValue),
	            new BasicNameValuePair("data[Brand][city_id]", ""),
	            new BasicNameValuePair("data[Brand][data[BrandAttribute][price_range]]", "empty"),
	            };
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(Arrays.asList(params),"UTF-8");
	    postRequest.setEntity(urlEncodedFormEntity);
		HttpResponse cityresponse = httpClient.execute(postRequest);

		logger.info("Response Code : "
		            + cityresponse.getStatusLine().getStatusCode());
		logger.info(cityresponse.getEntity().getContent());
		BufferedReader rd = new BufferedReader(new InputStreamReader(cityresponse.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}
		logger.info("The result when brand create in admin portal"+result.toString());

		int adminBrandId = Integer.parseInt(result.toString().replace("\"", ""));
		return adminBrandId;
	}

	protected HttpPost loginAdminPortal() throws JSONException {

		HttpPost request = new HttpPost(AdminProperties.loginUrl);
		JSONObject js = new JSONObject();
		JSONObject j = new JSONObject();
		js.put("email", AdminProperties.username);
		js.put("password",AdminProperties.password);
		j.put("User", js);
		StringEntity params =new StringEntity(j.toString(),ContentType.APPLICATION_JSON);
		request.setEntity(params);
		return request;
	}
}

     */
}
