package com.project.dao;

import com.project.ENUMS.RoomType;
import com.project.dto.RoomDTO;
import com.project.entities.Room;

import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Interface for Room
 */

public interface InterfaceForRoom {
    Room save(Room room);
    public void update(Room room);
    public void delete(Integer room_no);
    public Room getRoomByNo(Integer roomNo);
    public List<Room> getAllRooms();

    public Enum<RoomType> findRoomType(RoomDTO roomDTO);
}