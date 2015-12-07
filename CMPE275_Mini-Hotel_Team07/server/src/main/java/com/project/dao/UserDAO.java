package com.project.dao;
import com.project.ENUMS.UserType;
import com.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * User Data Access Object
 */

@Transactional
public class UserDAO implements InterfaceForUser {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User getUserById(Integer userId) {
        return null;
    }

    @Override
    public User getUserByName(String name) {
        return null;
    }

    @Override
    public User getUserByIdAndUserName(Integer userId, String username) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public String loginUser(User user) {
        String query = " from User u where u.user_name = ? and u.password = ?";
        List<User> users = (List<User>) hibernateTemplate.find(query,user.getUser_name(),user.getPassword());
        if (users.isEmpty()) {
            return "Invalid username or password";
            // return null;
        } else {
            System.out.println(users.get(0).getUser_type().toString());
            return users.get(0).getUser_type().toString();
        }
    }
}
