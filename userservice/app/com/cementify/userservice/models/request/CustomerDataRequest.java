package com.cementify.userservice.models.request;

import java.util.Date;

/**
 * Created by roshan on 29/02/16.
 */
public class CustomerDataRequest extends CustomerRequest {

    private Date birthDay;

    private String firstName;

    private String lastName;

    private Boolean isMale;

    private String notificationId;

    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

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

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

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
