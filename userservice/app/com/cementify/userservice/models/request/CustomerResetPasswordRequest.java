package com.cementify.userservice.models.request;

import play.data.validation.Constraints;

/**
 * Created by roshan on 28/02/16.
 */
public class CustomerResetPasswordRequest {

    @Constraints.Required
    private String mobile;

    @Constraints.Required
    @Constraints.MinLength(8)
    @Constraints.MaxLength(50)
    private String password;

    @Constraints.Required
    @Constraints.MinLength(8)
    @Constraints.MaxLength(50)
    private String oldPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
