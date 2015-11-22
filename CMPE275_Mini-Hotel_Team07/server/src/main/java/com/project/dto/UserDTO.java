package com.project.dto;

import java.lang.Enum;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar
 * User Table Data Transaction Object
 */

public class UserDTO{

    private Integer user_id;
    private String user_name;
    private String password;
    private Enum user_type;

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

    public Enum getUser_type() {
        return user_type;
    }

    public void setUser_type(Enum user_type) {
        this.user_type = user_type;
    }
}