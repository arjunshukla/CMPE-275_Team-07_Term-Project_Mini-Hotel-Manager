package com.project.dao;

import com.project.entities.Room;

import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Interface for Room
 */

public interface InterfaceForRoom {
    Room save(Room room);
    public Room update(Room room);
    public void delete(Room room);
    public Room getRoomByNo(Integer roomNo);
    public List<Room> getAllRooms();
}