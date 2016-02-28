package com.cementify.userservice.models.request;

import java.util.Date;

/**
 * Created by roshan on 29/02/16.
 */
public class CustomerDataUpdateRequest extends CustomerRequest {
    private Date birthDay;

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String firstName;

    private String lastName;

    private Boolean isMale;

    public Boolean getIsCodBlocked() {
        return isCodBlocked;
    }

    public void setIsCodBlocked(Boolean isCodBlocked) {
        this.isCodBlocked = isCodBlocked;
    }

    public Boolean getIsPrepaidBlocked() {
        return isPrepaidBlocked;
    }

    public void setIsPrepaidBlocked(Boolean isPrepaidBlocked) {
        this.isPrepaidBlocked = isPrepaidBlocked;
    }

    private Boolean isCodBlocked;

    private Boolean isPrepaidBlocked;

    public Boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(Boolean isMale) {
        this.isMale = isMale;
    }


}
