package com.cementify.userservice.models;

/**
 * Created by roshan on 29/02/16.
 */

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;

@Entity
@Table(name = "customer_contacts_table")
public class CustomerContact {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "contacts_id")
    private Integer  contactsId;

    @Column(name = "customer_id")
    private Integer customerId;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }


    @Column(name = "contact")
    private String contact;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date",insertable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date",insertable = false)
    private Date modifiedDate;

    public Integer getContactsId() {
        return contactsId;
    }

    public void setContactsId(Integer contactsId) {
        this.contactsId = contactsId;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }





}
