package com.project.entities;

import javax.persistence.*;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Guest Entity class
 */

@Entity
@Table(name = "guest")
public class Guest {

    @Id
    @Column(name = "guest_id", unique = true, nullable = false)
    private Integer guest_id;

    @Column(name = "guest_name", unique = false, nullable = false)
    private String guest_name;

    @Column(name = "guest_email", unique = true, nullable = false)
    private String guest_email;

    @Column(name = "license_no", unique = true, nullable = false)
    private String license_no;

    @Column(name = "street", unique = false, nullable = true)
    private String street;

    @Column(name = "city", unique = false, nullable = true)
    private String city;

    @Column(name = "state", unique = false, nullable = true)
    private String state;

    @Column(name = "zip", unique = false, nullable = true)
    private String zip;


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(Integer guest_id) {
        this.guest_id = guest_id;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getGuest_email() {
        return guest_email;
    }

    public void setGuest_email(String guest_email) {
        this.guest_email = guest_email;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }
}