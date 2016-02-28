package com.cementify.userservice.models;

import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;

/**
 * Created by roshan on 27/02/16.
 */
@Entity
@Table(name = "customer_device_table")
public class CustomerDevices {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Valid
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @Column(name = "device_id")
    private String deviceId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date",insertable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date",insertable = false)
    private Date modifiedDate;

    @Column(name = "os_type")
    private String osType;

    @Column(name = "notification_id")
    private String notificationId;

    @Column(name = "ruid")
    private String ruid;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getRuid() {
        return ruid;
    }

    public void setRuid(String ruid) {
        this.ruid = ruid;
    }


    public String validate() {

        return null;
    }

}
