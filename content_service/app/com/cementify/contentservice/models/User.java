package com.cementify.contentservice.models;

import play.data.validation.Constraints;

/**
 * Created by roshan on 28/02/16.
 */
public class User {
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Constraints.MinLength(8)
    @Constraints.MaxLength(20)
    @Constraints.Required
    private String mobile;

}
