package com.project.entities;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar
 * Person Entity class
 */

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", unique = true, nullable = false)
    private Integer person_id;

    @Column(name = "firstname", unique = false, nullable = false)
    private String firstname;

    @Column(name = "lastname", unique = false, nullable = false)
    private String lastname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "city", unique = false, nullable = true)
    private String city;

    @Column(name = "street", unique = false, nullable = true)
    private String street;

    @Column(name = "state", unique = false, nullable = true)
    private String state;

    @Column(name = "zip", unique = false, nullable = true)
    private String zip;

    @Column(name = "description", unique = false, nullable = true)
    private String description;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id")
    private Organization organization;


    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}