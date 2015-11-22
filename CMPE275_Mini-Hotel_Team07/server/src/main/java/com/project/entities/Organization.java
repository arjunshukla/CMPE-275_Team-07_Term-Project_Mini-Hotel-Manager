package com.project.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar
 * Organization Entity class
 */

@XmlRootElement
@Entity
@Table (name = "organization")
public class Organization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id", unique = true, nullable = false)
    private Integer org_id;

    @Column(name = "org_name", unique = false, nullable = false)
    private String org_name;

    @Column(name = "description", unique = false, nullable = true)
    private String org_description;

    @Column(name = "city", unique = false, nullable = true)
    private String city;

    @Column(name = "street", unique = false, nullable = true)
    private String street;

    @Column(name = "state", unique = false, nullable = true)
    private String state;

    @Column(name = "zip", unique = false, nullable = true)
    private String zip;


    public Integer getOrg_id() {
        return org_id;
    }

    public void setOrg_id(Integer org_id) {
        this.org_id = org_id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_description() {
        return org_description;
    }

    public void setOrg_description(String org_description) {
        this.org_description = org_description;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}