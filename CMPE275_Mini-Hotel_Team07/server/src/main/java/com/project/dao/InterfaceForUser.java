package com.project.dao;

import com.project.entities.User;

import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Interface for User
 */

public interface InterfaceForUser {

    public void save(User user);
    public void update(User user);
    public void delete(User user);
    public User getUserById(Integer userId);
    public User getUserByName(String name);
    public User getUserByIdAndUserName(Integer userId, String username);
    public List<User> getAllUsers();
    public String loginUser(User user);
    public List<User> verifyUserByUserName(String user_name);
}
