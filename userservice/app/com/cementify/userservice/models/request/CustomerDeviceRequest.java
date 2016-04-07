package com.cementify.userservice.models.request;

import java.util.Date;

/**
 * Created by roshan on 29/02/16.
 */
public class CustomerDeviceRequest extends CustomerRequest {


    private String firstName;

    private String lastName;

    private String notificationId;

    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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



}
