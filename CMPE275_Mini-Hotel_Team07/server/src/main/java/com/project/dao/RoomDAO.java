package com.project.dao;

import com.project.ENUMS.RoomType;
import com.project.dto.RoomDTO;
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
    public void delete(Room room) {
//        String query ="delete from Room where room_no=?";
//        hibernateTemplate.delete(query,room_no);
        hibernateTemplate.delete(room);
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

    @Override
    public Enum<RoomType> findRoomType(RoomDTO roomDTO) {
        Room room = new Room();
        Integer room_no = roomDTO.getRoom_no();
        System.out.println("RoomNo: "+room_no);
        room.setRoom_no(roomDTO.getRoom_no());
        String query = "from Room where room_no=?";
        List<Room> roomDetails = (List<Room>)hibernateTemplate.find(query,room_no);
        Enum<RoomType> roomType= roomDetails.get(0).getRoom_type();
        System.out.println("roomType: "+roomType);
        return roomType;
    }
}
