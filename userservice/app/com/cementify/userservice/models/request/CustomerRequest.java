package com.cementify.userservice.models.request;


import play.data.validation.Constraints;


/**
 * Created by roshan on 31/01/16.
 */
public class CustomerRequest {

    private String mobile;

    private String ruid;

    private Integer customerId;

    private String fbId;

    @Constraints.Email
    private String fbEmail;

    private String googleId;

    @Constraints.Email
    private String googleEmail;

    @Constraints.Email
    private String email;

    private Boolean isVerified;


   @Constraints.MinLength(8)
   @Constraints.MaxLength(50)
    private String password;


    private String osType;



    public String getRuid() {
        return ruid;
    }

    public void setRuid(String ruid) {
        this.ruid = ruid;
    }



    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getFbId() {
        return fbId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFbEmail(String fbEmail) {
        this.fbEmail = fbEmail;
    }

    public void setGoogleEmail(String googleEmail) {
        this.googleEmail = googleEmail;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getFbEmail() {
        return fbEmail;
    }

    public String getGoogleEmail() {
        return googleEmail;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
