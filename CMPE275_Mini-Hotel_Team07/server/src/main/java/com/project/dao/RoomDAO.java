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
        Integer room_id = (Integer)hibernateTemplate.save(room);
        return room;
    }

    @Override
    public void update(Room room) {
        hibernateTemplate.update(room);
    }

    @Override
    public void delete(Integer room_no) {
        String query ="delete from rooms where room_no=?";
        hibernateTemplate.delete(query);
    }

    @Override
    public Room getRoomByNo(Integer room_no) {
        String query = "from Room r where r.room_no = ?";
        @SuppressWarnings("unchecked")
        List<Room> room = (List<Room>) hibernateTemplate.find(query,room_no);
        if (room.isEmpty()) {
            return null;
        } else {
            System.out.println();
            return room.get(0);
        }
    }

    @Override
    public List<Room> getAllRooms() {
        String query = "from Room r";
        List<Room> roomsList = (List<Room>) hibernateTemplate.find(query);
        if(roomsList.isEmpty()){
            return null;
        }
        else{
            return roomsList;
        }

    }
}
