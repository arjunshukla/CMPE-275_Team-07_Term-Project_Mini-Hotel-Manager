package com.project.dto;

import com.project.entities.Friendship;

import java.util.List;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar
 * Person Table Data Transaction Object
 */

public class PersonDTO {

    private Integer person_id;
    private String firstname;
    private String lastname;
    private String email;
    private AddressDTO addressDTO;
    private String description;
    private Integer org_id;
    private List<Friendship> Friendship;


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

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrg_id() {
        return org_id;
    }

    public void setOrg_id(Integer org_id) {
        this.org_id = org_id;
    }

    public List<Friendship> getFriendship() {
        return Friendship;
    }

    public void setFriendship(List<Friendship> friendship) {
        Friendship = friendship;
    }
}