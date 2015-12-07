package com.project.dao;

import com.project.entities.Friendship;
import com.project.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar
 * Friendship Table Data Access Object
 */

@Transactional
public class FriendshipDAO implements InterfaceForFriendship{

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public void save(Friendship friend) {

        hibernateTemplate.save(friend);

    }


    @Override
    public boolean checkFriendshipStatus(Integer friend1, Integer friend2) {

        // 1. query frienship table if f1->f2 and f2->f1

        String query = "from Friendship f where f.friend1 = ? and f.friend2 = ?";
        System.out.println("in check friendship with query: "+query);

        List<Friendship> friendList1 = (List<Friendship>) hibernateTemplate.find(query,friend1,friend2);


        List<Friendship> friendList2 = (List<Friendship>) hibernateTemplate.find(query,friend2,friend1);

        if (friendList1.isEmpty() && friendList2.isEmpty())
            return false;
        else
            return true;
    }

    @Override
    public Person getPersonById(Integer person_id) {
        String query = "from Person where person_id = ?";
        @SuppressWarnings("unchecked")
        List<Person> persons = (List<Person>) hibernateTemplate.find(query,person_id);
        if (persons.isEmpty()) {
            return null;
        } else {
            System.out.println();
            return persons.get(0);
        }
    }

    @Override
    public void remove(Friendship friendship) {
        hibernateTemplate.delete(friendship);
    }

    @Override
    public ArrayList<Friendship> getFriendsForId(Integer person_id) {
        String query = "select friend2 from Friendship where friend1 = ?";
        @SuppressWarnings("unchecked")
        List<Friendship> friends = (List<Friendship>) hibernateTemplate.find(query,person_id);

        if (friends.isEmpty()) {
            return null;
        } else {
            System.out.println();
            return (ArrayList)friends;
        }
    }

    @Override
    public ArrayList<Integer> getFriendIdsForId(Integer person_id) {
        String query = "select friend2 from Friendship where friend1 = ?";
        @SuppressWarnings("unchecked")
        List<Integer> friends = (List<Integer>) hibernateTemplate.find(query,person_id);

        if (friends.isEmpty()) {
            return null;
        } else {
            System.out.println();
            return (ArrayList)friends;
        }
    }

}
