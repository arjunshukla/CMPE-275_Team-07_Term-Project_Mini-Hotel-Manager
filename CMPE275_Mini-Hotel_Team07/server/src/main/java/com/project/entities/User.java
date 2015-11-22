package com.project.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.project.ENUMS.UserType;

import javax.persistence.*;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * User Entity class
 */

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name="user_id", unique=true, nullable=false)
    private Integer user_id;

    @Column(name="user_name", unique=true, nullable=false)
    private String user_name;

    @Column(name="password", unique=false, nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="user_type", unique=false, nullable=false)
    private UserType user_type;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUser_type() {
        return user_type;
    }

    public void setUser_type(UserType user_type) {
        this.user_type = user_type;
    }
}
