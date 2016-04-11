package com.cementify.userservice.models.response;

import java.util.Date;

/**
 * Created by roshan on 26/03/16.
 */
public class CustomerResponseData {

    private Integer customerId;

    private Date birthday;

    private Integer age;

    private String aboutUser;

    private String profilePic;

    private Boolean isMale;

    private String userName;


    public  CustomerResponseData(){

    }

    public  CustomerResponseData(Integer customerId ,Date birthday,Integer age,String aboutUser,
                                 String profilePic,Boolean isMale,String userName){
        this.customerId=customerId;
        this.birthday=birthday;
        this.age=age;
        this.aboutUser=aboutUser;
        this.profilePic=profilePic;
        this.isMale=isMale;
        this.userName=userName;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(Boolean isMale) {
        this.isMale = isMale;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
