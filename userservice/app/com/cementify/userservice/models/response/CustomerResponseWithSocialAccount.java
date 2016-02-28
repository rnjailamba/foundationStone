package com.cementify.userservice.models.response;

/**
 * Created by roshan on 29/02/16.
 */
public class CustomerResponseWithSocialAccount extends  CustomerResponse{
    private String fbEmail;

    private String googleId;

    private String googleEmail;

    private String fbId;

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
