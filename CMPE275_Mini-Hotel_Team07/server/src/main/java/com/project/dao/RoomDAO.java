package com.project.dao;

import com.project.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Room Data Access Object
 */

@Transactional
public class RoomDAO implements InterfaceForRoom {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public Room save(Room room) {
        return null;
    }

    @Override
    public void update(Room room) {

    }

    @Override
    public void delete(Room room) {

    }

    @Override
    public Room getRoomByNo(Integer roomNo) {
        return null;
    }

    @Override
    public List<Room> getAllRooms() {
        return null;
    }
}
