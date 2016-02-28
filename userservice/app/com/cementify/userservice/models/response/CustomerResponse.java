package com.cementify.userservice.models.response;

/**
 * Created by roshan on 31/01/16.
 */
public class CustomerResponse {

    private String mobile;

    private Integer customerId;

    private String fbId;

    private String ruid;

    private String fbEmail;

    private String googleId;

    private String googleEmail;

    private String email;

    private Boolean isVerified;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setFbEmail(String fbEmail) {
        this.fbEmail = fbEmail;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public void setGoogleEmail(String googleEmail) {
        this.googleEmail = googleEmail;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getRuid() {
        return ruid;
    }

    public void setRuid(String ruid) {
        this.ruid = ruid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getFbEmail() {
        return fbEmail;
    }

    public String getFbId() {
        return fbId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getGoogleEmail() {
        return googleEmail;
    }
}
