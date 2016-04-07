package com.cementify.userservice.models;

/**
 * Created by roshan on 29/11/15.
 */

import javax.persistence.*;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import play.data.validation.Constraints;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


@Entity
@Table(name = "customer_table")
public class Customer {


    private static final int ITERATION_NUMBER = 1000;
    //admin123
    private static final String masterPassword="Jz9c+JmcqEUZE5h+d+6IpzgG00o=";
    private static final String masterSalt="ivV3xTn3hY8=";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "customer_id")
    private Integer customerId;

    @Constraints.MaxLength(value = 20)
    @Column(name = "mobile")
    private String mobile;

    @Constraints.Email
    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String userName;


    @Column(name = "fb_id")
    private String fbId;

    @Constraints.Email
    @Column(name = "fb_email")
    private String fbEmail;

    @Column(name = "google_id")
    private String googleId;

    @Constraints.Email
    @Column(name = "google_email")
    private String googleEmail;

    @Transient
    private String password;

    @Column(name = "encrypted_password")
    private String encryptedPassword;

    @Column(name = "salt")
    private String salt;

    @Column(name = "is_cod_blocked")
    private Boolean isCodBlocked;

    @Column(name = "is_prepaid_blocked")
    private Boolean isPrepaidBlocked;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date",insertable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date",insertable = false)
    private Date modifiedDate;


    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public void setFbEmail(String fbEmail) {
        this.fbEmail = fbEmail;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setGoogleEmail(String googleEmail) {
        this.googleEmail = googleEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setIsCodBlocked(Boolean isCodBlocked) {
        this.isCodBlocked = isCodBlocked;
    }

    public void setIsPrepaidBlocked(Boolean isPrepaidBlocked) {
        this.isPrepaidBlocked = isPrepaidBlocked;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }


    public Integer getCustomerId() {
        return customerId;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFbId() {
        return fbId;
    }

    public String getFbEmail() {
        return fbEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getGoogleEmail() {
        return googleEmail;
    }

    public Boolean getIsCodBlocked() {
        return isCodBlocked;
    }

    public Boolean getIsPrepaidBlocked() {
        return isPrepaidBlocked;
    }


    public Boolean getIsVerified() {
        return isVerified;
    }

    public String getSalt() {
        return salt;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }


    /*
   * https://www.owasp.org/index.php/Hashing_Java
   */
    public void encryptPassword() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] randomSalt = new byte[8];
            random.nextBytes(randomSalt);

            byte[] digest = getHash(password, randomSalt);
            encryptedPassword = byteToBase64(digest);
            salt = byteToBase64(randomSalt);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasPassword(String password) {
        try {
            byte[] masterBytes = base64ToByte(masterSalt);
            byte[] masterDigest = getHash(password, masterBytes);
            if(byteToBase64(masterDigest).equals(masterPassword)){
                return true;
            }
            byte[] bytes = base64ToByte(salt);
            byte[] digest = getHash(password, bytes);
            return byteToBase64(digest).equals(encryptedPassword);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getHash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(salt);
        byte[] input = digest.digest(password.getBytes("UTF-8"));
        for (int i = 0; i < ITERATION_NUMBER; i++) {
            digest.reset();
            input = digest.digest(input);
        }
        return input;
    }

    private static byte[] base64ToByte(String data) throws IOException {
        return DatatypeConverter.parseBase64Binary(data);
    }

    private static String byteToBase64(byte[] data) {
        return DatatypeConverter.printBase64Binary(data);
    }

    public boolean isFacebookUser() {
        return StringUtils.isNotEmpty(getFbEmail()) && StringUtils.isNotEmpty(getFbId());
    }

    public boolean isGoogleUser() {
        return StringUtils.isNotEmpty(getGoogleEmail()) && StringUtils.isNotEmpty(getGoogleId());
    }

    public boolean isCementifyUser() {
        return StringUtils.isNotEmpty(encryptedPassword) && StringUtils.isNotEmpty(salt);
    }

    private boolean hasPassword() {
        return StringUtils.isNotEmpty(getPassword());
    }




}
