package com.project.implementation;

import com.project.dao.InterfaceForFriendship;

import com.project.dao.InterfaceForPersons;
import com.project.dto.FriendshipDTO;
import com.project.entities.Friendship;
import com.project.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar
 * Friendship implementation class
 */

public class FriendshipImplementation {

    @Autowired
    InterfaceForFriendship friendshipDao;

    public String updateFriendship(Integer friend1, Integer friend2) {

        /* 1. check if both exist -> incase of error for invalid user return the invalid user
           2. check if already friends -> return success and do nothing
           3. if both are satisfied then record friendship -> insert
        * */

        // check if both exist
        if(friendshipDao.getPersonById(friend1)!=null){
            if(friendshipDao.getPersonById(friend2)!=null){

                // Check if already friends
                if(!(friendshipDao.checkFriendshipStatus(friend1,friend2))){
                    System.out.println("They are not friends");
                    Friendship friendship = new Friendship();

                    Person person1 = friendshipDao.getPersonById(friend1);
                    Person person2 = friendshipDao.getPersonById(friend2);

                    friendship.setFriend1(person1.getPerson_id());
                    friendship.setFriend2(person2.getPerson_id());

                    friendshipDao.save(friendship);

                    friendship.setFriend1(person2.getPerson_id());
                    friendship.setFriend2(person1.getPerson_id());
                    friendshipDao.save(friendship);

                    return "success: friendship recorded";

                } else
                    return "success: already friends";
            } else
                return friend2+" doesn't exist";
        } else
        return friend1+" doesn't exist";
    }

    public String removeFriendship(Integer friend1, Integer friend2) {
        if(friendshipDao.getPersonById(friend1)!=null){
            if(friendshipDao.getPersonById(friend2)!=null){

                // Check if already friends
                if((friendshipDao.checkFriendshipStatus(friend1,friend2))){
                    System.out.println("They are friends");
                    Friendship friendship = new Friendship();

                    Person person1 = friendshipDao.getPersonById(friend1);
                    Person person2 = friendshipDao.getPersonById(friend2);

                    friendship.setFriend1(person1.getPerson_id());
                    friendship.setFriend2(person2.getPerson_id());

                    friendshipDao.remove(friendship);

                    friendship.setFriend1(person2.getPerson_id());
                    friendship.setFriend2(person1.getPerson_id());
                    friendshipDao.remove(friendship);

                    return "success: friendship deleted";

                } else
                    return "success: not friends";
            } else
                return friend2+" doesn't exist";
        } else
            return friend1+" doesn't exist";
    }
}
