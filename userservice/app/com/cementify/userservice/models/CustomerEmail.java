package com.cementify.userservice.models;

import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;

/**
 * Created by roshan on 29/02/16.
 */

@Entity
@Table(name = "customer_emails_table")
public class CustomerEmail {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "email_id")
    private Integer emailId;


    @Constraints.Required
    @Column(name = "customer_id")
    private Integer customerId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Constraints.Required
    @Constraints.Email
    @Column(name = "email")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date",insertable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date",insertable = false)
    private Date modifiedDate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }





}
