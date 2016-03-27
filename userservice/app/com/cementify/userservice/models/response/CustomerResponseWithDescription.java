package com.cementify.userservice.models.response;

import java.util.Date;

/**
 * Created by roshan on 26/03/16.
 */
public class CustomerResponseWithDescription extends CustomerResponse {

    private Date birthday;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
