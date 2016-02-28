package com.cementify.userservice.models.response;

/**
 * Created by roshan on 31/01/16.
 */
public class CustomerResponse {

    private String mobile;

    private Integer customerId;

    private String ruid;

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


}
